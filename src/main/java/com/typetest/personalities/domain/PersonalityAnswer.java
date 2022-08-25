package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.AnswerDto;
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
    private Integer point;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    private String answerImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TypeIndicator typeIndicator;

    public boolean checkSameValue(AnswerDto answerDto) {
        return  (this.answer == null ?
                        answerDto.getAnswer() == null :
                        this.answer.equals(answerDto.getAnswer())) &&
                (this.answerImage == null ?
                        answerDto.getAnswerImage() == null :
                        this.answerImage.equals(answerDto.getAnswerImage())) &&
                this.point == answerDto.getPoint() &&
                this.tendency == answerDto.getTendency() &&
                this.typeIndicator.getId() == answerDto.getTypeIndicatorId();
    }

    public void updateAnswerInfo(AnswerDto answerDto) {
        this.answer = answerDto.getAnswer();
        this.point = answerDto.getPoint();
        this.tendency = answerDto.getTendency();
        this.answerImage = answerDto.getAnswerImage();
        this.typeIndicator = answerDto.getTypeIndicator();
    }

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
