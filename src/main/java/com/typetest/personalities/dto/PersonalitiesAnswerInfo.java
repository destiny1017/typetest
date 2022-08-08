package com.typetest.personalities.dto;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter @Setter
@ToString
public class PersonalitiesAnswerInfo {
    private Long userId;
    private AnswerType answerType;
    private HashMap<Integer, Integer> answer;
    private TestCodeInfo testCodeInfo;
}
