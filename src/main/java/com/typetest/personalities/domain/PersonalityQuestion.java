package com.typetest.personalities.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityQuestion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private String question;

    private int num;

    private String questionImage;

    public PersonalityQuestion(TestCodeInfo testCode, String question, int num) {
        this.testCode = testCode;
        this.question = question;
        this.num = num;
    }

    public PersonalityQuestion(TestCodeInfo testCode, String question, int num, String questionImage) {
        this.testCode = testCode;
        this.question = question;
        this.num = num;
        this.questionImage = questionImage;
    }
}
