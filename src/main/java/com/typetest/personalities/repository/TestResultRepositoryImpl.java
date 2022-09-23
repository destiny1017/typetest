package com.typetest.personalities.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.typetest.user.domain.User;
import com.typetest.mypage.dto.QTypeInfoData;
import com.typetest.mypage.dto.TypeInfoData;

import java.util.List;

import static com.typetest.personalities.domain.QTestResult.testResult;
import static com.typetest.personalities.domain.QTestCodeInfo.*;
import static com.typetest.personalities.domain.QTypeInfo.typeInfo;


public class TestResultRepositoryImpl implements TestResultRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public TestResultRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<TypeInfoData> getUserTypeList(User user) {
        return jpaQueryFactory
                .select(new QTypeInfoData(
                    testCodeInfo.testCode,
                    testCodeInfo.testName,
                    testResult.typeInfo,
                    testResult.createDate))
                .from(testResult, typeInfo, testCodeInfo)
                .where(
                        testResult.typeInfo.eq(typeInfo),
                        testResult.testCode.eq(testCodeInfo),
                        testResult.user.eq(user))
                .fetch();
    }
}
