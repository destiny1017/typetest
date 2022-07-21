package com.typetest.personalities.service;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.data.ExamPointTable;
import com.typetest.personalities.data.PointWrapper;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.PersonalityTypeDetail;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.repository.TestCode;
import com.typetest.personalities.repository.PersonalityTypeDetailRepository;
import com.typetest.personalities.repository.PersonalityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Primary
public class PersonalityTestServiceImpl implements PersonalityTestService {

    private final PersonalityTypeRepository personalityTypeRepository;
    private final PersonalityTypeDetailRepository personalityTypeDetailRepository;
    private final LoginRepository loginRepository;

    @Override
    public String calcType(PersonalitiesAnswerInfo answerInfo) {
        TestCode code = answerInfo.getTestCode();
        String type = "";
        if (code.equals(TestCode.EXAM)) {
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
        TestCode code = answerInfo.getTestCode();
        Map<Integer, Integer> answer = answerInfo.getAnswer();
        Optional<User> byId = loginRepository.findById(userId);
        User user;
        if(byId.isPresent()) {
            user = byId.get();
            PersonalityType pt = new PersonalityType(user, code, type);
            personalityTypeRepository.save(pt);
            for (int key : answer.keySet()) {
                PersonalityTypeDetail ptd = new PersonalityTypeDetail(user, code, key, answer.get(key));
                personalityTypeDetailRepository.save(ptd);
            }
        } else {
            throw new NotFoundEntityException("[" + userId + "] 사용자를 찾을 수 없습니다.");
        }

    }


}
