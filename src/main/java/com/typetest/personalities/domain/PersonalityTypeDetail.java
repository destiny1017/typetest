package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.data.AnswerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityTypeDetail {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private PersonalityType personalityType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    private int num;
    private int answer;

    public PersonalityTypeDetail(PersonalityType personalityType, User user, AnswerType answerType, int num, int answer) {
        this.personalityType = personalityType;
        this.user = user;
        this.answerType = answerType;
        this.num = num;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "PersonalityTypeDetail{" +
                "testCode=" + answerType +
                ", num=" + num +
                ", answer=" + answer +
                '}';
    }
}
