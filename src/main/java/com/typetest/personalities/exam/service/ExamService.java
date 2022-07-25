package com.typetest.personalities.exam.service;

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

@Slf4j
@Service
@Transactional(readOnly = true)
@Qualifier("examService")
public class ExamService {

    private List<List<ExamQuestionInfo>> questions;
    private List<ExamQuestionInfo> questionSlide;
    private int questionCnt;
    private int questionSlideCnt;

    public ExamService() {
        // 질문 목록 생성(추후 DB로 변경)
        questions = new ArrayList<>();
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


        // slide question create
        questionSlide = new ArrayList<>();
        ArrayList<String> answers1 = new ArrayList<>();
        ArrayList<Integer> pointsSlide1 = new ArrayList<>(Arrays.asList(new Integer[]{1, 3, 5}));
        answers1.add("Example answer1");
        answers1.add("Example answer2");
        answers1.add("Example answer3");

        ArrayList<String> answers2 = new ArrayList<>();
        ArrayList<Integer> pointsSlide2 = new ArrayList<>(Arrays.asList(new Integer[]{1, 5}));
        answers2.add("Example answer1");
        answers2.add("Example answer2");

        questionSlide.add(new ExamQuestionInfo(1, pointsSlide1,
                "Example slide question1", answers1));
        questionSlide.add(new ExamQuestionInfo(2, pointsSlide2,
                "Example slide question2", answers2));
        questionSlide.add(new ExamQuestionInfo(3, pointsSlide2,
                "Example slide question3", answers2));
        questionSlide.add(new ExamQuestionInfo(4, pointsSlide1,
                "Example slide question4", answers1));
        questionSlide.add(new ExamQuestionInfo(5, pointsSlide2,
                "Example slide question5", answers2));
        questionSlide.add(new ExamQuestionInfo(6, pointsSlide1,
                "Example slide question6", answers1));
        questionSlide.add(new ExamQuestionInfo(7, pointsSlide1,
                "Example slide question7", answers1));
        questionSlide.add(new ExamQuestionInfo(8, pointsSlide2,
                "Example slide question8", answers2));
        questionSlide.add(new ExamQuestionInfo(9, pointsSlide1,
                "Example slide question9", answers1));

        // 질문 개수 계산
        questionCnt = questions.stream().mapToInt(i -> i.size()).sum();
        questionSlideCnt = questionSlide.size();
    }

    public int getQuestionCnt() {
        return questionCnt;
    }

    public int getQuestionSlideCnt() {
        return questionSlideCnt;
    }

    public List<List<ExamQuestionInfo>> createQuestions() {
        return questions;
    }

    public List<ExamQuestionInfo> createQuestionSlide() {
        return questionSlide;
    }

    public ExamResultInfo getResult(String type) {
        ExamResultInfo examResultInfo = new ExamResultInfo();
//        String imgSrc = "https://img.freepik.com/premium-vector/character-illustrations-of-students-learning-together_276340-157.jpg?w=740";
        String imgSrc = "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200";
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
