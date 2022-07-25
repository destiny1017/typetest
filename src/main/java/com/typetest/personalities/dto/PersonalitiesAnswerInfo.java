package com.typetest.personalities.dto;

import com.typetest.personalities.data.TestCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter @Setter
@ToString
public class PersonalitiesAnswerInfo {
    Long userId;
    TestCode testCode;
    HashMap<Integer, Integer> answer;
}
