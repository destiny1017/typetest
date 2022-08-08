package com.typetest.personalities.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.typetest.login.domain.User;
import com.typetest.mypage.dto.QTypeInfoData;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.domain.QPersonalityType;
import com.typetest.personalities.domain.QTestCodeInfo;
import com.typetest.personalities.domain.QTypeInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.typetest.personalities.domain.QPersonalityType.personalityType;
import static com.typetest.personalities.domain.QTestCodeInfo.*;
import static com.typetest.personalities.domain.QTypeInfo.typeInfo;


public class PersonalityTypeRepositoryImpl implements PersonalityTypeRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public PersonalityTypeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<TypeInfoData> getUserTypeList(User user) {
        return jpaQueryFactory
                .select(new QTypeInfoData(
                    testCodeInfo.testCode,
                    testCodeInfo.testName,
                    personalityType.typeCode,
                    typeInfo.typeName,
                    personalityType.createDate))
                .from(personalityType, typeInfo, testCodeInfo)
                .where(
                        personalityType.typeCode.eq(typeInfo.typeCode),
                        personalityType.testCode.eq(testCodeInfo),
                        personalityType.user.eq(user))
                .fetch();
    }
}
