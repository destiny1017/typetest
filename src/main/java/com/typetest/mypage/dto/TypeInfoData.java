package com.typetest.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.typetest.personalities.data.TestCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TypeInfoData {
    private TestCode testCode;
    private String typeCode;
    private String typeName;

    @QueryProjection
    public TypeInfoData(TestCode testCode, String typeCode, String typeName) {
        this.testCode = testCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
