package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.personalities.domain.TestCodeInfo;

import java.util.List;

public interface TestAdminService {
    TestInfoDto createTestInfoDto(String testCode);
    List<TestCodeInfo> findAllTestInfo();
    TestInfoDto saveTestInfo(TestInfoDto testInfoDto);
}
