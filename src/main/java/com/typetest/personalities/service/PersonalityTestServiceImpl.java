package com.typetest.personalities.service;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.data.ExamPointTable;
import com.typetest.personalities.data.PointWrapper;
import com.typetest.personalities.domain.TestResult;
import com.typetest.personalities.domain.TestResultDetail;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeInfo;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.repository.TestResultDetailRepository;
import com.typetest.personalities.repository.TestResultRepository;
import com.typetest.personalities.repository.TypeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
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

    @Override
    public String calcType(PersonalitiesAnswerInfo answerInfo) {
        AnswerType code = answerInfo.getAnswerType();
        String type = "";
        if (code.equals(AnswerType.EXAM)) {
            // 테스트 코드에 해당하는 포인트테이블 호출
            ExamPointTable examPointTable = new ExamPointTable();
            // 해당 테스트의 점수 배정식 호출
            List<PointWrapper> allocator = examPointTable.getAllocator();
            // 사용자가 선택한 응답 데이터
            HashMap<Integer, Integer> answer = answerInfo.getAnswer();
            // 응답 데이터와 점수 배정식으로 통합 점수 산정
//            answer.forEach((key, value) -> allocator.get(key).addPoint(value));
            for (Integer key : answer.keySet()) {
                allocator.get(key).addPoint(answer.get(key));
            }
            // 합산된 점수로 유형 계산하여 반환
            type = examPointTable.getType();
        }
        return type;
    }

    @Override
    public void saveTestInfo(PersonalitiesAnswerInfo answerInfo, String type) throws NotFoundEntityException {
        Long userId = answerInfo.getUserId();
//        AnswerType code = answerInfo.getAnswerType();
        TestCodeInfo code = answerInfo.getTestCodeInfo();
        Map<Integer, Integer> answer = answerInfo.getAnswer();
        Optional<User> byId = loginRepository.findById(userId);
        User user;
        if(byId.isPresent()) {
            user = byId.get();
            Optional<TypeInfo> typeInfo = typeInfoRepository.findByTypeCode(type);
            if(typeInfo.isPresent()) {
                TestResult pt = new TestResult(user, code, typeInfo.get());
                testResultRepository.save(pt); // 유형정보 저장
                for (int key : answer.keySet()) {
                    TestResultDetail ptd = new TestResultDetail(pt, user, code, key, answer.get(key));
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
