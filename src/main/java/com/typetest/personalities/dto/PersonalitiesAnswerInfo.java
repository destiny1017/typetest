package com.typetest.personalities.dto;

import com.typetest.personalities.exam.repository.TestCode;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter @ToString
public class PersonalitiesAnswerInfo {
    Long userId;
    TestCode testCode;
    HashMap<Integer, Integer> answer;
}
