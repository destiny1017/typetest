package com.typetest.personalities.exam.dto;

import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.PersonalityAnswer;
import lombok.Getter;

@Getter
public class ExamAnswerDto {

    private String answer;
    private Long answerId;
    private Tendency tendency;
    private String answerImage;

    public ExamAnswerDto(String answer, Long answerId, Tendency tendency, String answerImage) {
        this.answer = answer;
        this.answerId = answerId;
        this.tendency = tendency;
        this.answerImage = answerImage;
    }

    public ExamAnswerDto(PersonalityAnswer personalityAnswer) {
        this.answer = personalityAnswer.getAnswer();
        this.answerId = personalityAnswer.getId();
        this.tendency = personalityAnswer.getTendency();
        this.answerImage = personalityAnswer.getAnswerImage();
    }
}
