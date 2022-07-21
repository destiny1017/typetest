package com.typetest.personalities.exam.service;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@Qualifier("examService")
@RequiredArgsConstructor
public class ExamService {

    public List<ExamQuestionInfo> createQuestions() {
        List<ExamQuestionInfo> questions = new ArrayList<>();
        ArrayList<Integer> points = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));

        questions.add(new ExamQuestionInfo(1, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(2, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(3, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(4, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(5, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(6, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(7, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(8, points,
                "This is a question example for Test..."));
        questions.add(new ExamQuestionInfo(9, points,
                "This is a question example for Test..."));

        return questions;
    }
}
