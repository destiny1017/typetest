package com.typetest.mypage.data;

import com.querydsl.core.annotations.QueryProjection;
import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class TypeInfoData {
    private String testCode;
    private String testName;
    private TypeInfo typeInfo;
    private LocalDateTime createDate;
    private String formattedDate;

    @QueryProjection
    public TypeInfoData(String testCode, String testName, TypeInfo typeInfo, LocalDateTime createDate) {
        this.testCode = testCode;
        this.testName = testName;
        this.typeInfo = typeInfo;
        this.createDate = createDate;
        this.formattedDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
