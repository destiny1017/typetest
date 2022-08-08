package com.typetest.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.typetest.personalities.data.AnswerType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TypeInfoData {
    private String testCode;
    private String testName;
    private String typeCode;
    private String typeName;
    private LocalDateTime createDate;

    @QueryProjection
    public TypeInfoData(String testCode, String testName, String typeCode, String typeName, LocalDateTime createDate) {
        this.testCode = testCode;
        this.testName = testName;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.createDate = createDate;
    }
}
