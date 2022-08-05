package com.typetest.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.typetest.personalities.data.TestCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TypeInfoData {
    private TestCode testCode;
    private String typeCode;
    private String typeName;
    private LocalDateTime createDate;

    @QueryProjection
    public TypeInfoData(TestCode testCode, String typeCode, LocalDateTime createDate, String typeName) {
        this.testCode = testCode;
        this.typeCode = typeCode;
        this.createDate = createDate;
        this.typeName = typeName;
    }
}
