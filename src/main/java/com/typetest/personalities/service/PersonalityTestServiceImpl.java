package com.typetest.personalities.service;

import com.typetest.personalities.data.ExamPointTable;
import com.typetest.personalities.data.PointWrapper;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.repository.TestCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PersonalityTestServiceImpl implements PersonalityTestService {

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
            Map<Integer, Integer> answer = answerInfo.getAnswer();
            // 응답 데이터와 점수 배정식으로 통합 점수 산정
            for (int key : answer.keySet()) {
                allocator.get(key).addPoint(answer.get(key));
            }
            // 합산된 점수로 유형 계산하여 반환
            type = examPointTable.getType();
        }
        return type;
    }

}
