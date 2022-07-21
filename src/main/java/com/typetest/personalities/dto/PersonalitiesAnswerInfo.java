package com.typetest.personalities.dto;

import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.repository.TestCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@ToString
public class PersonalitiesAnswerInfo {
    Long userId;
    TestCode testCode;
    HashMap<Integer, Integer> answer;
}
