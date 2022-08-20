package com.typetest.admin.testadmin.data;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class TestInfoDto {
    private String testCode;
    private String testName;
    private AnswerType answerType;
    private String image;
    private String description;

    public TestInfoDto() {
    }

    public TestInfoDto(TestCodeInfo testCodeInfo) {
        this.testCode = testCodeInfo.getTestCode();
        this.testName = testCodeInfo.getTestName();
        this.answerType = testCodeInfo.getAnswerType();
        this.image = testCodeInfo.getImage();
        this.description = testCodeInfo.getDescription();
    }
}
