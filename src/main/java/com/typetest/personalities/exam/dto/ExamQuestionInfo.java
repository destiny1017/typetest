package com.typetest.personalities.exam.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ExamQuestionInfo {

    private int questionNum;
    private List<Integer> selectPoint;
    private Integer selectNum;
    private String question;

    public ExamQuestionInfo(int questionNum, List<Integer> selectAnswer, String question) {
        this.questionNum = questionNum;
        this.selectPoint = selectAnswer;
        this.question = question;
    }
}
