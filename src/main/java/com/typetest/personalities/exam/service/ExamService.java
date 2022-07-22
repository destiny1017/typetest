package com.typetest.personalities.exam.service;

import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public List<List<ExamQuestionInfo>> createQuestions() {
        List<List<ExamQuestionInfo>> questions = new ArrayList<>();
        ArrayList<Integer> points = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
        ArrayList<ExamQuestionInfo> questions1 = new ArrayList<>();
        ArrayList<ExamQuestionInfo> questions2 = new ArrayList<>();
        ArrayList<ExamQuestionInfo> questions3 = new ArrayList<>();
        questions1.add(new ExamQuestionInfo(1, points,
                "Example question1"));
        questions1.add(new ExamQuestionInfo(2, points,
                "Example question2"));
        questions1.add(new ExamQuestionInfo(3, points,
                "Example question3"));
        questions1.add(new ExamQuestionInfo(4, points,
                "Example question4"));
        questions1.add(new ExamQuestionInfo(5, points,
                "Example question5"));
        questions1.add(new ExamQuestionInfo(6, points,
                "Example question6"));
        questions1.add(new ExamQuestionInfo(7, points,
                "Example question7"));
        questions1.add(new ExamQuestionInfo(8, points,
                "Example question8"));
        questions1.add(new ExamQuestionInfo(9, points,
                "Example question9"));

        questions2.add(new ExamQuestionInfo(10, points,
                "Example question10"));
        questions2.add(new ExamQuestionInfo(11, points,
                "Example question11"));
        questions2.add(new ExamQuestionInfo(12, points,
                "Example question12"));
        questions2.add(new ExamQuestionInfo(13, points,
                "Example question13"));
        questions2.add(new ExamQuestionInfo(14, points,
                "Example question14"));
        questions2.add(new ExamQuestionInfo(15, points,
                "Example question15"));
        questions2.add(new ExamQuestionInfo(16, points,
                "Example question16"));
        questions2.add(new ExamQuestionInfo(17, points,
                "Example question17"));
        questions2.add(new ExamQuestionInfo(18, points,
                "Example question18"));

        questions3.add(new ExamQuestionInfo(19, points,
                "Example question19"));
        questions3.add(new ExamQuestionInfo(20, points,
                "Example question20"));
        questions3.add(new ExamQuestionInfo(21, points,
                "Example question21"));
        questions3.add(new ExamQuestionInfo(22, points,
                "Example question22"));
        questions3.add(new ExamQuestionInfo(23, points,
                "Example question23"));
        questions3.add(new ExamQuestionInfo(24, points,
                "Example question24"));
        questions3.add(new ExamQuestionInfo(25, points,
                "Example question25"));
        questions3.add(new ExamQuestionInfo(26, points,
                "Example question26"));
        questions3.add(new ExamQuestionInfo(27, points,
                "Example question27"));

        questions.add(questions1);
        questions.add(questions2);
        questions.add(questions3);

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
