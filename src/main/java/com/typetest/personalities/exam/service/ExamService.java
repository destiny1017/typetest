package com.typetest.personalities.exam.service;

import com.typetest.personalities.exam.dto.ExamQuestionDto;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExamService {
    List getQuestions(String testCode);
    Long getQuestionCnt(String testCode);
    Page<ExamQuestionDto> getQuestionsPage(String testCode);
}
