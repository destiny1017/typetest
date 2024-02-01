package com.typetest.personalities.data;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@ToString
public class PersonalitiesAnswerInfo {
    private Long userId;
    private AnswerType answerType;
    private Map<Integer, Long> answer;
    private TestCodeInfo testCodeInfo;
    private String type;

    @Builder
    public PersonalitiesAnswerInfo(Long userId, AnswerType answerType, Map<Integer, Long> answer, TestCodeInfo testCodeInfo, String type) {
        this.userId = userId;
        this.answerType = answerType;
        this.answer = answer;
        this.testCodeInfo = testCodeInfo;
        this.type = type;
    }

    public static PersonalitiesAnswerInfo of(Map<String, String> answerMapParam, TestCodeInfo testCodeInfo) {
        HashMap<Integer, Long> answerMap = new HashMap<>();
        answerMapParam.forEach((key, value) -> answerMap.put(Integer.parseInt(key), Long.parseLong(value)));
        return PersonalitiesAnswerInfo.builder()
                .answer(answerMap)
                .answerType(testCodeInfo.getAnswerType())
                .testCodeInfo(testCodeInfo)
                .build();
    }
}
