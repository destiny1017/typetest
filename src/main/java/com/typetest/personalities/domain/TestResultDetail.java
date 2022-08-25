package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class TestResultDetail {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private TestResult testResult;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TestCodeInfo testCode;

    private Integer num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PersonalityAnswer personalityAnswer;

    public TestResultDetail(TestResult testResult, User user, TestCodeInfo testCode, int num, PersonalityAnswer personalityAnswer) {
        this.testResult = testResult;
        this.user = user;
        this.testCode = testCode;
        this.num = num;
        this.personalityAnswer = personalityAnswer;
    }

    @Override
    public String toString() {
        return "TestResultDetail{" +
                ", num=" + num +
                ", answer=" + personalityAnswer +
                '}';
    }
}
