package com.typetest.personalities.service;

import com.typetest.common.constant.ErrorCode;
import com.typetest.common.exception.NotFoundEntityException;
import com.typetest.common.exception.TypetestException;
import com.typetest.personalities.data.*;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
@Transactional(readOnly = true)
public class PersonalityTestServiceImpl implements PersonalityTestService {

    private final TestResultRepository testResultRepository;
    private final TestResultDetailRepository testResultDetailRepository;
    private final UserRepository userRepository;
    private final TypeInfoRepository typeInfoRepository;
    private final IndicatorSettingRepository indicatorSettingRepository;
    private final PersonalityAnswerRepository personalityAnswerRepository;
    private final TestCodeInfoRepository testCodeInfoRepository;
    private final PersonalityQuestionRepository personalityQuestionRepository;

    @Override
    public List getQuestions(String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        if(testCodeInfo.getAnswerType() == AnswerType.EXAM) {
            return createPagedQuestions(testCodeInfo);
        } else if(testCodeInfo.getAnswerType() == AnswerType.CARD) {
            return createSerializedQuestions(testCodeInfo);
        }
        return new ArrayList<>();
    }

    private List<ExamQuestionDto> createSerializedQuestions(TestCodeInfo testCodeInfo) {
        List<PersonalityQuestion> questions = personalityQuestionRepository.findByTestCode(testCodeInfo);
        List<ExamQuestionDto> questionDtos = new ArrayList<>();
        questions.stream().forEach(i -> questionDtos.add(new ExamQuestionDto(i)));
        return questionDtos;
    }

    private List<List<ExamQuestionDto>> createPagedQuestions(TestCodeInfo testCodeInfo) {
        List<PersonalityQuestion> questions = personalityQuestionRepository.findByTestCode(testCodeInfo);
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
    }

    @Override
    public Page<ExamQuestionDto> getQuestionsPage(String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        return personalityQuestionRepository.findByTestCode(testCodeInfo, PageRequest.of(0, 10));
    }

    @Override
    public Long getQuestionCnt(String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        return personalityQuestionRepository.countByTestCode(testCodeInfo);
    }

    /**
     * ## 사용자 응답정보와 지표정보를 통해 해당하는 유형을 도출하는 메서드
     * @param answer {질문번호 : PersonalityAnswer@Id}
     * @return type
     */
    @Override
    public String calcType(Map<Integer, Long> answer) {
        List<PersonalityAnswer> answerList = personalityAnswerRepository.findByIdIn(answer.values());
        Map<TypeIndicator, Integer> pointMap = new HashMap<>();
        StringBuffer type = new StringBuffer();

        // 응답받은 답변 엔티티들의 점수 합산
        answerList.stream().forEach(selectedAnswer ->
                // 선택한 답변에 해당하는 indicator를 key로, 선택한 답변의 point를 value로 합산
                pointMap.put(selectedAnswer.getTypeIndicator(),
                pointMap.getOrDefault(selectedAnswer.getTypeIndicator(), 0) + selectedAnswer.getPoint()));

        // pointMap을 indicatorNum순서로 정렬
        List<TypeIndicator> sortedPointMap = pointMap.keySet().stream()
                .sorted((s1, s2) -> s1.getIndicatorNum().compareTo(s2.getIndicatorNum())).collect(Collectors.toList());

        // 0 index row 하나만 받아오기위한 pageRequest(= limit 1)
        PageRequest pr = PageRequest.of(0, 1);

        // indicator별 결과를 type에 차례대로 append
        sortedPointMap.stream().forEach(indicator ->
                type.append(indicatorSettingRepository
                        .findByTypeIndicatorAndCuttingPointLessThanOrderByCuttingPointDesc(
                                indicator, pointMap.get(indicator), pr)
                        .get(0).getResult()));

        return type.toString();
    }

    @Override
    @Transactional
    public void saveTestInfo(PersonalitiesAnswerInfo answerInfo) throws NotFoundEntityException {
        Long userId = answerInfo.getUserId();
        String type = answerInfo.getType();
        TestCodeInfo testCode = answerInfo.getTestCodeInfo();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, userId.toString()));
        TypeInfo typeInfo = typeInfoRepository.findByTestCodeAndTypeCode(testCode, type)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode.getTestCode(), type));

        // 유형정보 저장
        TestResult testResult = new TestResult(user, testCode, typeInfo);
        testResultRepository.save(testResult);

        // 테스트 상세 응답 정보 저장
        Map<Integer, Long> answer = answerInfo.getAnswer();
        for (int key : answer.keySet()) {
            Optional<PersonalityAnswer> findAnswer = personalityAnswerRepository.findById(answer.get(key));
            TestResultDetail ptd = new TestResultDetail(testResult, user, testCode, key, findAnswer.get());
            testResultDetailRepository.save(ptd);
        }
    }

    @Override
    public TestResultDto createTestResultInfo(String testCode, String type) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        TypeInfo typeInfo = typeInfoRepository.findByTestCodeAndTypeCode(testCodeInfo, type)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode, type));
        TestResultDto testResultDto = new TestResultDto(typeInfo);
        double rate = ((double) typeInfo.getResultCount() / testCodeInfo.getPlayCount());
        testResultDto.setTypeRate(new DecimalFormat("#.#%").format(rate));
        return testResultDto;
    }

    @Override
    @Transactional
    public void plusResultCount(TestCodeInfo testCodeInfo, String type) {
        TypeInfo typeInfo = typeInfoRepository.findByTestCodeAndTypeCode(testCodeInfo, type)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCodeInfo.getTestCode(), type));
        testCodeInfoRepository.plusPlayCount(testCodeInfo);
        typeInfoRepository.plusResultCount(typeInfo);
    }

}
