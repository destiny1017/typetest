package com.typetest.personalities.exam.service;

import com.typetest.personalities.domain.PersonalityAnswer;
import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ExamService {

    List<List<PersonalityQuestion>> getQuestions(String testCode);

    Long getQuestionCnt(String testCode);

    ExamResultInfo getResult(String type, String testCode);

    Page<PersonalityQuestion> getQuestionsPage(String testCode);
}
