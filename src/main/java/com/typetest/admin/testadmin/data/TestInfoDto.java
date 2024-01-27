package com.typetest.admin.testadmin.data;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@ToString
public class TestInfoDto {

    @NotEmpty
    private String testCode;
    @NotEmpty
    private String testName;

    @NotNull
    private AnswerType answerType;

    private String image;
    private String description;
    private String thumbnailDesc;

    private Long playCount;

    @NotNull
    private Integer active;

    public TestInfoDto() {
    }

    @Builder
    public TestInfoDto(String testCode, String testName, AnswerType answerType, String image, String description, String thumbnailDesc, Long playCount, Integer active) {
        this.testCode = testCode;
        this.testName = testName;
        this.answerType = answerType;
        this.image = image;
        this.description = description;
        this.thumbnailDesc = thumbnailDesc;
        this.playCount = playCount;
        this.active = active;
    }

    public TestInfoDto(TestCodeInfo testCodeInfo) {
        this.testCode = testCodeInfo.getTestCode();
        this.testName = testCodeInfo.getTestName();
        this.answerType = testCodeInfo.getAnswerType();
        this.image = testCodeInfo.getImage();
        this.description = testCodeInfo.getDescription();
        this.thumbnailDesc = testCodeInfo.getThumbnailDesc();
        this.playCount = testCodeInfo.getPlayCount();
        this.active = testCodeInfo.getActive();
    }

    public String playCountStr() {
        String count1 = String.valueOf(Double.valueOf(playCount) / 10000); // 0.0000
        int dotIndex = count1.indexOf(".");
        return count1.substring(0, dotIndex + 2) + "만";
    }
}
