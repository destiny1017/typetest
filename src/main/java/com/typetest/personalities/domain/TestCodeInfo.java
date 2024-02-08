package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.personalities.data.AnswerType;
import lombok.Builder;
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
    private String thumbnailDesc;
    private Integer active;
    private long playCount;

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

    @Builder
    public TestCodeInfo(String testCode, String testName, AnswerType answerType, String image,
                        String description, String thumbnailDesc, Integer active, long playCount) {
        this.testCode = testCode;
        this.testName = testName;
        this.answerType = answerType;
        this.image = image;
        this.description = description;
        this.thumbnailDesc = thumbnailDesc;
        this.active = active;
        this.playCount = playCount;
    }

    public void setPlayCount(int count) {
        this.playCount = this.playCount + count;
    }
}
