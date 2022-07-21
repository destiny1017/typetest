package com.typetest.personalities.exam.service;

import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public ExamResultInfo getResult(String type) {
        ExamResultInfo examResultInfo = new ExamResultInfo();
        String imgSrc = "https://img.freepik.com/premium-vector/character-illustrations-of-students-learning-together_276340-157.jpg?w=740";
        switch (type) {
            case "AAA":
                examResultInfo.setType("AAA");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "BBB":
                examResultInfo.setType("BBB");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "ABB":
                examResultInfo.setType("ABB");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "AAB":
                examResultInfo.setType("AAB");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "BAA":
                examResultInfo.setType("BAA");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "BBA":
                examResultInfo.setType("BBA");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "ABA":
                examResultInfo.setType("ABA");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
            case "BAB":
                examResultInfo.setType("BAB");
                examResultInfo.setDescription("description!");
                examResultInfo.setImageUrl(imgSrc);
                break;
        }
        return examResultInfo;
    }
}
