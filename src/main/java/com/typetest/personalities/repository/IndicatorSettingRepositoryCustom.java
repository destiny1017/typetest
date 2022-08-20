package com.typetest.personalities.repository;

import com.typetest.admin.testadmin.data.IndicatorInfoDto;
import com.typetest.personalities.domain.TestCodeInfo;

import java.util.List;

public interface IndicatorSettingRepositoryCustom {
    List<IndicatorInfoDto> getIndicatorInfoList(TestCodeInfo testCodeInfo);
}
