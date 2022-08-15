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

    private int num;
    private int answer;

    public TestResultDetail(TestResult testResult, User user, TestCodeInfo testCode, int num, int answer) {
        this.testResult = testResult;
        this.user = user;
        this.testCode = testCode;
        this.num = num;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "TestResultDetail{" +
                ", num=" + num +
                ", answer=" + answer +
                '}';
    }
}
