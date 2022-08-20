package com.typetest.personalities.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.typetest.admin.testadmin.data.IndicatorInfoDto;
import com.typetest.admin.testadmin.data.QIndicatorInfoDto;
import com.typetest.personalities.domain.TestCodeInfo;

import java.util.ArrayList;
import java.util.List;

import static com.typetest.personalities.domain.QIndicatorSetting.indicatorSetting;
import static com.typetest.personalities.domain.QTypeIndicator.typeIndicator;

public class IndicatorSettingRepositoryRepositoryImpl implements IndicatorSettingRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public IndicatorSettingRepositoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<IndicatorInfoDto> getIndicatorInfoList(TestCodeInfo testCodeInfo) {
        return jpaQueryFactory
                .select(new QIndicatorInfoDto(
                    typeIndicator.indicatorNum,
                    typeIndicator.indicatorName,
                    indicatorSetting.cuttingPoint,
                    indicatorSetting.result))
                .from(indicatorSetting)
                .rightJoin(indicatorSetting.typeIndicator, typeIndicator)
                .where(typeIndicator.testCode.eq(testCodeInfo))
                .fetch();
    }
}
