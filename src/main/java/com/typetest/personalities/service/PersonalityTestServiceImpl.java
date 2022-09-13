package com.typetest.personalities.service;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.ExamQuestionDto;
import com.typetest.personalities.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Primary
public class PersonalityTestServiceImpl implements PersonalityTestService {

    private final TestResultRepository testResultRepository;
    private final TestResultDetailRepository testResultDetailRepository;
    private final LoginRepository loginRepository;
    private final TypeInfoRepository typeInfoRepository;
    private final IndicatorSettingRepository indicatorSettingRepository;
    private final PersonalityAnswerRepository personalityAnswerRepository;
    private final TestCodeInfoRepository testCodeInfoRepository;
    private final PersonalityQuestionRepository personalityQuestionRepository;

    @Override
    public List getQuestions(String testCode) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        if(testCodeInfoOp.isPresent()) {
            TestCodeInfo testCodeInfo = testCodeInfoOp.get();
            List<PersonalityQuestion> questions = personalityQuestionRepository.findByTestCode(testCodeInfo);
            if(testCodeInfo.getAnswerType() == AnswerType.EXAM) {
                List<List<ExamQuestionDto>> pageQuestions = new ArrayList<>();
                int cnt = -1;
                for (int i = 0; i < questions.size(); i++) {
                    if((i) % 10 == 0) {
                        pageQuestions.add(new ArrayList<>());
                        cnt++; // 10개 추가될 때 마다 다음 인덱스에 담기
                    }
                    pageQuestions.get(cnt).add(new ExamQuestionDto(questions.get(i)));
                }
                return pageQuestions;
            } else if(testCodeInfo.getAnswerType() == AnswerType.CARD) {
                List<ExamQuestionDto> questionDtos = new ArrayList<>();
                questions.stream().forEach(i -> questionDtos.add(new ExamQuestionDto(i)));
                return questionDtos;
            }
            return null;
        } else {
            throw new NotFoundEntityException("테스트코드 [" + testCode + "] 에 해당하는 테스트정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public Page<ExamQuestionDto> getQuestionsPage(String testCode) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        if(testCodeInfoOp.isPresent()) {
            Page<ExamQuestionDto> questions = personalityQuestionRepository.findByTestCode(testCodeInfoOp.get(), PageRequest.of(0, 10));
            return questions;
        } else {
            throw new NotFoundEntityException("테스트코드 [" + testCode + "] 에 해당하는 질문데이터를 찾을 수 없습니다.");
        }
    }

    @Override
    public Long getQuestionCnt(String testCode) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        if (testCodeInfoOp.isPresent()) {
            return personalityQuestionRepository.countByTestCode(testCodeInfoOp.get());
        } else {
            throw new NotFoundEntityException("테스트코드 [" + testCode + "] 에 해당하는 테스트정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public String calcType(PersonalitiesAnswerInfo answerInfo) {
        HashMap<Integer, Long> answer = answerInfo.getAnswer();
        List<PersonalityAnswer> answerList = personalityAnswerRepository.findByIdIn(answer.values());
        Map<TypeIndicator, Integer> pointMap = new LinkedHashMap<>();
        StringBuffer type = new StringBuffer();

        // 응답받은 답변 엔티티들의 점수 합산
        answerList.stream().forEach(selectedAnswer ->
                // 선택한 답변에 해당하는 indicator를 key로, 선택한 답변의 point를 value로
                pointMap.put(selectedAnswer.getTypeIndicator(),
                        pointMap.getOrDefault(selectedAnswer.getTypeIndicator(), 0) + selectedAnswer.getPoint()));

        // result 하나만 받아올 pageRequest
        PageRequest pr = PageRequest.of(0, 1);

        // indicator별 결과를 type에 차례대로 append
        pointMap.forEach((indicator, point) ->
                type.append(indicatorSettingRepository
                        .findByTypeIndicatorAndCuttingPointLessThanOrderByCuttingPointDesc(indicator, point, pr)
                        .get(0).getResult()));

        return type.toString();
    }

    @Override
    public void saveTestInfo(PersonalitiesAnswerInfo answerInfo, String type) throws NotFoundEntityException {
        Long userId = answerInfo.getUserId();
        TestCodeInfo testCode = answerInfo.getTestCodeInfo();
        Map<Integer, Long> answer = answerInfo.getAnswer();
        Optional<User> byId = loginRepository.findById(userId);
        User user;
        if(byId.isPresent()) {
            user = byId.get();
            Optional<TypeInfo> typeInfo = typeInfoRepository.findByTestCodeAndTypeCode(testCode, type);
            if(typeInfo.isPresent()) {
                TestResult pt = new TestResult(user, testCode, typeInfo.get());
                testResultRepository.save(pt); // 유형정보 저장
                for (int key : answer.keySet()) {
                    Optional<PersonalityAnswer> findAnswer = personalityAnswerRepository.findById(answer.get(key));
                    TestResultDetail ptd = new TestResultDetail(pt, user, testCode, key, findAnswer.get());
                    testResultDetailRepository.save(ptd); // 테스트 상세 응답 정보 저장
                }
            } else {
                throw new NotFoundEntityException("[" + type + "] 에 해당하는 유형정보를 찾을 수 없습니다.");
            }
        } else {
            throw new NotFoundEntityException("[" + userId + "] 사용자를 찾을 수 없습니다.");
        }

    }

    @Override
    public TestResultDto createTestResultInfo(String testCode, String type) {
        Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
        if(testCodeInfo.isPresent()) {
            Optional<TypeInfo> typeInfo = typeInfoRepository.findByTestCodeAndTypeCode(testCodeInfo.get(), type);
            if(typeInfo.isPresent()) {
                TestResultDto testResultDto = new TestResultDto(typeInfo.get());
                double rate = ((double) typeInfo.get().getResultCount() / testCodeInfo.get().getPlayCount());
                testResultDto.setTypeRate(new DecimalFormat("#.#%").format(rate));
                return testResultDto;
            } else {
                throw new NotFoundEntityException("[" + testCode + "] 에 해당하는 테스트 정보를 찾을 수 없습니다.");
            }
        } else {
            throw new NotFoundEntityException("[" + type + "] 에 해당하는 유형정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public void plusResultCount(TestCodeInfo testCodeInfo, String type) {
        Optional<TypeInfo> typeInfoOp = typeInfoRepository.findByTestCodeAndTypeCode(testCodeInfo, type);
        testCodeInfoRepository.plusPlayCount(testCodeInfo);

        if (typeInfoOp.isPresent()) {
            TypeInfo typeInfo = typeInfoOp.get();
            typeInfoRepository.plusResultCount(typeInfo);
        } else {
            throw new NotFoundEntityException("[" + type + "] 에 해당하는 유형정보를 찾을 수 없습니다.");
        }

    }
}
