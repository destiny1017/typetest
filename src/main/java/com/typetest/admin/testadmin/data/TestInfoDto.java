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
    private String thumbnailDesc;
    private Long playCount;
    private Integer active;

    public TestInfoDto() {
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
