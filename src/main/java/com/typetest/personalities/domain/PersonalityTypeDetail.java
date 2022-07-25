package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.data.TestCode;
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
    private TestCode testCode;

    private int num;
    private int answer;

    public PersonalityTypeDetail(PersonalityType personalityType, User user, TestCode testCode, int num, int answer) {
        this.personalityType = personalityType;
        this.user = user;
        this.testCode = testCode;
        this.num = num;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "PersonalityTypeDetail{" +
                "testCode=" + testCode +
                ", num=" + num +
                ", answer=" + answer +
                '}';
    }
}
