package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TestInfoDto;
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

    private String image;
    private String description;
    private Integer active;
    private Long playCount;

    public TestCodeInfo(String testCode, String testName, AnswerType answerType) {
        this.testCode = testCode;
        this.testName = testName;
        this.answerType = answerType;
    }

    public TestCodeInfo(String testCode, String testName, AnswerType answerType, String image, String description, Integer active) {
        this.testCode = testCode;
        this.testName = testName;
        this.answerType = answerType;
        this.image = image;
        this.description = description;
        this.active = active;
    }

    public TestCodeInfo(TestInfoDto testInfoDto) {
        this.testCode = testInfoDto.getTestCode();
        this.testName = testInfoDto.getTestName();
        this.answerType = testInfoDto.getAnswerType();
        this.image = testInfoDto.getImage();
        this.description = testInfoDto.getDescription();
        this.active = testInfoDto.getActive();
    }
}
