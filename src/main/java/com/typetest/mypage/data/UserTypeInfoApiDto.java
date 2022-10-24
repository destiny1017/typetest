package com.typetest.mypage.data;

import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserTypeInfoApiDto {
    private String testCode;
    private String testName;
    private LocalDateTime createDate;
    private String formattedDate;

    public UserTypeInfoApiDto(TypeInfoData typeInfoData) {
        this.testCode = typeInfoData.getTestCode();
        this.testName = typeInfoData.getTestName();
        this.createDate = typeInfoData.getCreateDate();
        this.formattedDate = typeInfoData.getFormattedDate();
    }
}
