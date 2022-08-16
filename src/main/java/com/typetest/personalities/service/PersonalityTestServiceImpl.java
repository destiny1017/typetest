package com.typetest.personalities.service;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.data.ExamPointTable;
import com.typetest.personalities.data.PointWrapper;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Primary
public class PersonalityTestServiceImpl implements PersonalityTestService {

    private final TestResultRepository testResultRepository;
    private final TestResultDetailRepository testResultDetailRepository;
    private final LoginRepository loginRepository;
    private final TypeInfoRepository typeInfoRepository;
    private final TypeIndicatorRepository typeIndicatorRepository;
    private final IndicatorSettingRepository indicatorSettingRepository;
    private final PersonalityQuestionRepository personalityQuestionRepository;
    private final PersonalityAnswerRepository personalityAnswerRepository;

//    @Override
//    public String calcType(PersonalitiesAnswerInfo answerInfo) {
//        AnswerType code = answerInfo.getAnswerType();
//        String type = "";
//        if (code.equals(AnswerType.EXAM)) {
//            // 테스트 코드에 해당하는 포인트테이블 호출
//            ExamPointTable examPointTable = new ExamPointTable();
//            // 해당 테스트의 점수 배정식 호출
//            List<PointWrapper> allocator = examPointTable.getAllocator();
//            // 사용자가 선택한 응답 데이터
//            HashMap<Integer, Integer> answer = answerInfo.getAnswer();
//            // 응답 데이터와 점수 배정식으로 통합 점수 산정
////            answer.forEach((key, value) -> allocator.get(key).addPoint(value));
//            for (Integer key : answer.keySet()) {
//                allocator.get(key).addPoint(answer.get(key));
//            }
//            // 합산된 점수로 유형 계산하여 반환
//            type = examPointTable.getType();
//        }
//        return type;
//    }

    @Override
    public String calcType(PersonalitiesAnswerInfo answerInfo) {
//        TestCodeInfo testCodeInfo = answerInfo.getTestCodeInfo();
        HashMap<Integer, Long> answer = answerInfo.getAnswer();
        List<PersonalityAnswer> answerList = personalityAnswerRepository.findByIdIn(answer.values());
//        List<PersonalityQuestion> questions = personalityQuestionRepository.findByTestCodeOrderByNum(testCodeInfo);
        Map<TypeIndicator, Integer> pointMap = new LinkedHashMap<>();
        StringBuffer type = new StringBuffer();

        // 응답받은 답변 엔티티들의 점수 합산
        answerList.stream().forEach(selectedAnswer ->
                // 선택한 답변에 해당하는 indicator를 key로, 선택한 답변의 point를 value로
                pointMap.put(selectedAnswer.getTypeIndicator(),
                        pointMap.getOrDefault(selectedAnswer.getTypeIndicator(), 0) + selectedAnswer.getPoint()));

//        // 번호대로 정렬한 질문 수만큼 응답받은 답변 점수 합산
//        questions.stream().forEach(question ->
//                // 질문의 지표값을 키로, 답변에서 현재 질문 번호에 해당하는 응답 값을 밸류로 합산
//                pointMap.put(question.getTypeIndicator(),
//                    pointMap.getOrDefault(question.getTypeIndicator(), 0) + answer.get(question.getNum())));

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
        TestCodeInfo code = answerInfo.getTestCodeInfo();
        Map<Integer, Long> answer = answerInfo.getAnswer();
        Optional<User> byId = loginRepository.findById(userId);
        User user;
        if(byId.isPresent()) {
            user = byId.get();
            Optional<TypeInfo> typeInfo = typeInfoRepository.findByTypeCode(type);
            if(typeInfo.isPresent()) {
                TestResult pt = new TestResult(user, code, typeInfo.get());
                testResultRepository.save(pt); // 유형정보 저장
                for (int key : answer.keySet()) {
                    Optional<PersonalityAnswer> findAnswer = personalityAnswerRepository.findById(answer.get(key));
                    TestResultDetail ptd = new TestResultDetail(pt, user, code, key, findAnswer.get());
                    testResultDetailRepository.save(ptd); // 테스트 상세 응답 정보 저장
                }
            } else {
                throw new NotFoundEntityException("[" + type + "] 에 해당하는 유형정보를 찾을 수 없습니다.");
            }
        } else {
            throw new NotFoundEntityException("[" + userId + "] 사용자를 찾을 수 없습니다.");
        }

    }


}
