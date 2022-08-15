package com.typetest.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TypeInfoData {
    private String testCode;
    private String testName;
    private TypeInfo typeInfo;
    private String typeName;
    private LocalDateTime createDate;

    @QueryProjection
    public TypeInfoData(String testCode, String testName, TypeInfo typeInfo, String typeName, LocalDateTime createDate) {
        this.testCode = testCode;
        this.testName = testName;
        this.typeInfo = typeInfo;
        this.typeName = typeName;
        this.createDate = createDate;
    }
}
