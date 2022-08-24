package com.typetest.admin.testadmin.data;

import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.PersonalityAnswer;
import com.typetest.personalities.domain.TypeIndicator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    private String answer;
    private Integer point;
    private String answerImage;
    private Tendency tendency;
    private TypeIndicator typeIndicator;

    public AnswerDto(PersonalityAnswer answer) {
        this.id = answer.getId();
        this.answer = answer.getAnswer();
        this.point = answer.getPoint();
        this.answerImage = answer.getAnswerImage();
        this.tendency = answer.getTendency();
        this.typeIndicator = answer.getTypeIndicator();
    }
}
