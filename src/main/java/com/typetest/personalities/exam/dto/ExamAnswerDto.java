package com.typetest.personalities.exam.dto;

import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.PersonalityAnswer;
import lombok.Getter;

@Getter
public class ExamAnswerDto {

    private String answer;
    private int point;
    private Tendency tendency;
    private String answerImage;

    public ExamAnswerDto(String answer, int point, Tendency tendency, String answerImage) {
        this.answer = answer;
        this.point = point;
        this.tendency = tendency;
        this.answerImage = answerImage;
    }

    public ExamAnswerDto(PersonalityAnswer personalityAnswer) {
        this.answer = personalityAnswer.getAnswer();
        this.point = personalityAnswer.getPoint();
        this.tendency = personalityAnswer.getTendency();
        this.answerImage = personalityAnswer.getAnswerImage();
    }
}
