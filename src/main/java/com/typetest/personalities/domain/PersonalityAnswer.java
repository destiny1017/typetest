package com.typetest.personalities.domain;

import com.typetest.personalities.data.Tendency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityAnswer {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PersonalityQuestion personalityQuestion;

    private String answer;
    private int point;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    private String answerImage;

    public PersonalityAnswer(PersonalityQuestion personalityQuestion, String answer, int point, Tendency tendency, String answerImage) {
        this.personalityQuestion = personalityQuestion;
        this.answer = answer;
        this.point = point;
        this.tendency = tendency;
        this.answerImage = answerImage;
    }

    public PersonalityAnswer(PersonalityQuestion personalityQuestion, String answer, int point, Tendency tendency) {
        this.personalityQuestion = personalityQuestion;
        this.answer = answer;
        this.point = point;
        this.tendency = tendency;
    }

}
