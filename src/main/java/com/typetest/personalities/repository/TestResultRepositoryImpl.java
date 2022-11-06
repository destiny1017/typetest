package com.typetest.personalities.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.QPersonalityAnswer;
import com.typetest.personalities.domain.QTestResultDetail;
import com.typetest.user.domain.User;
import com.typetest.mypage.data.QTypeInfoData;
import com.typetest.mypage.data.TypeInfoData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.typetest.personalities.domain.QPersonalityAnswer.*;
import static com.typetest.personalities.domain.QTestResult.testResult;
import static com.typetest.personalities.domain.QTestCodeInfo.*;
import static com.typetest.personalities.domain.QTestResultDetail.*;
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

    @Override
    public Map<Tendency, Long> countTendency(User user) {
        HashMap<Tendency, Long> tendencyMap = new HashMap();
        List<Tuple> fetch = jpaQueryFactory
                .select(personalityAnswer.tendency, testResultDetail.count())
                .from(testResultDetail, personalityAnswer)
                .where(testResultDetail.personalityAnswer.eq(personalityAnswer)
                        , testResultDetail.user.eq(user))
                .groupBy(personalityAnswer.tendency)
                .fetch();
        fetch.stream().forEach(tuple -> {
            tendencyMap.put(tuple.get(0, Tendency.class), tuple.get(1, Long.class));
        });

        return tendencyMap;
    }
}
