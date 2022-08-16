package com.typetest.personalities.repository;

import com.typetest.login.domain.User;
import com.typetest.mypage.dto.TypeInfoData;

import java.util.List;

public interface TestResultRepositoryCustom {
    List<TypeInfoData> getUserTypeList(User user);
}