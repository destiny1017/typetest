package com.typetest.personalities.domain;

import com.typetest.personalities.data.Tendency;
import lombok.Builder;
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
    @JoinColumn(name = "PERSONALITY_QUESTION_ID")
    private PersonalityQuestion personalityQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private String answer;
    private int point;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    private String answerImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TypeIndicator typeIndicator;

    public void setQuestion(PersonalityQuestion question) {
        this.personalityQuestion = question;
    }

    @Builder
    public PersonalityAnswer(PersonalityQuestion personalityQuestion, TestCodeInfo testCode, String answer, int point, Tendency tendency, String answerImage, TypeIndicator typeIndicator) {
        this.personalityQuestion = personalityQuestion;
        this.testCode = testCode;
        this.answer = answer;
        this.point = point;
        this.tendency = tendency;
        this.answerImage = answerImage;
        this.typeIndicator = typeIndicator;
    }
}
