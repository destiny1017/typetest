package com.typetest.personalities.repository;

import com.typetest.personalities.data.Tendency;
import com.typetest.user.domain.User;
import com.typetest.mypage.data.TypeInfoData;

import java.util.List;
import java.util.Map;

public interface TestResultRepositoryCustom {
    List<TypeInfoData> getUserTypeList(User user);
    Map<Tendency, Long> countTendency(User user);
}
