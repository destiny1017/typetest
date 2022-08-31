package com.typetest.personalities.data;

import com.typetest.personalities.domain.PersonalityQuestion;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExamQuestionDto {
    private String question;
    private int num;
    private String questionImage;
    private List<ExamAnswerDto> answerList;

    public ExamQuestionDto(String question, int num, String questionImage, List<ExamAnswerDto> answerList) {
        this.question = question;
        this.num = num;
        this.questionImage = questionImage;
        this.answerList = answerList;
    }

    public ExamQuestionDto(PersonalityQuestion personalityQuestion) {
        this.question = personalityQuestion.getQuestion();
        this.num = personalityQuestion.getNum();
        this.questionImage = personalityQuestion.getQuestionImage();
        this.answerList = personalityQuestion.getAnswerList().stream()
                .map(ExamAnswerDto::new)
                .collect(Collectors.toList());
    }
}
