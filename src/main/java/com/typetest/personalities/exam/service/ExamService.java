package com.typetest.personalities.exam.service;

import com.typetest.personalities.exam.dto.ExamQuestionDto;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExamService {

    List<List<ExamQuestionDto>> getQuestions(String testCode);

    Long getQuestionCnt(String testCode);

    ExamResultInfo getResult(String type, String testCode);

    Page<ExamQuestionDto> getQuestionsPage(String testCode);
}
