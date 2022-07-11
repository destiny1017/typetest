package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.exam.repository.TestCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityTypeDetail {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    private TestCode testCode;

    private int num;
    private int answer;

    public PersonalityTypeDetail(User user, TestCode testCode, int num,  int answer) {
        this.user = user;
        this.testCode = testCode;
        this.num = num;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "PersonalityTypeDetail{" +
                "id=" + id +
                ", testCode=" + testCode +
                ", num=" + num +
                ", answer=" + answer +
                '}';
    }
}
