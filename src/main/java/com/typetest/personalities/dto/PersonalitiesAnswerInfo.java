package com.typetest.personalities.dto;

import com.typetest.personalities.exam.repository.TestCode;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Data
@Getter @ToString
public class PersonalitiesAnswerInfo {
    String userId;
    TestCode testCode;
    Map<String, Integer> answer;
}
