package com.typetest.personalities.domain;

import com.typetest.personalities.data.AnswerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
public class TestCodeInfo {

    @Id
    private String testCode;

    private String testName;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    public TestCodeInfo(String testCode, String testName, AnswerType answerType) {
        this.testCode = testCode;
        this.testName = testName;
        this.answerType = answerType;
    }
}
