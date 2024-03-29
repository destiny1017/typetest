package com.typetest.personalities.service;

import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.data.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.ExamQuestionDto;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PersonalityTestService {
    List getQuestions(String testCode);
    Long getQuestionCnt(String testCode);
    Page<ExamQuestionDto> getQuestionsPage(String testCode);
    String calcType(Map<Integer, Long> answer);
    void saveTestInfo(PersonalitiesAnswerInfo answerInfo);
    TestResultDto createTestResultInfo(String testCode, String type);
    void plusResultCount(TestCodeInfo testCodeInfo, String type);
}
