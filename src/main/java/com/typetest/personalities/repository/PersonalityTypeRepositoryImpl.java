package com.typetest.personalities.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.typetest.login.domain.User;
import com.typetest.mypage.dto.QTypeInfoData;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.domain.QPersonalityType;
import com.typetest.personalities.domain.QTypeInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.typetest.personalities.domain.QPersonalityType.personalityType;
import static com.typetest.personalities.domain.QTypeInfo.typeInfo;


public class PersonalityTypeRepositoryImpl implements PersonalityTypeRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public PersonalityTypeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<TypeInfoData> getUserTypeList(User user) {
        return jpaQueryFactory
                .select(new QTypeInfoData(
                    personalityType.answerType,
                    personalityType.typeCode,
                    personalityType.createDate,
                    typeInfo.typeName))
                .from(personalityType, typeInfo)
                .where(
                        personalityType.typeCode.eq(typeInfo.typeCode),
                        personalityType.user.eq(user))
                .fetch();
    }
}
