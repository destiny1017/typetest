package com.typetest.mypage.data;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.typetest.mypage.dto.QTypeInfoData is a Querydsl Projection type for TypeInfoData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTypeInfoData extends ConstructorExpression<TypeInfoData> {

    private static final long serialVersionUID = 1349191583L;

    public QTypeInfoData(com.querydsl.core.types.Expression<String> testCode, com.querydsl.core.types.Expression<String> testName, com.querydsl.core.types.Expression<? extends com.typetest.personalities.domain.TypeInfo> typeInfo, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate) {
        super(TypeInfoData.class, new Class<?>[]{String.class, String.class, com.typetest.personalities.domain.TypeInfo.class, java.time.LocalDateTime.class}, testCode, testName, typeInfo, createDate);
    }

}

