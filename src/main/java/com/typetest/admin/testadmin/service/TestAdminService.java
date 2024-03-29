package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.QuestionDto;
import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.admin.testadmin.data.TypeIndicatorDto;
import com.typetest.admin.testadmin.data.TypeInfoDto;
import com.typetest.common.constant.ResultCode;
import com.typetest.personalities.domain.TestCodeInfo;

import java.util.List;

public interface TestAdminService {
    TestInfoDto createTestInfoDto(String testCode);
    List<TestCodeInfo> findAllTestInfo();
    void saveTestInfo(TestInfoDto testInfoDto);
    List<TypeIndicatorDto> findIndicatorInfo(String testCode);
    ResultCode saveIndicatorInfo(List<TypeIndicatorDto> indicatorDtoList, String testCode);
    void saveQuestionInfo(List<QuestionDto> questionDtoList, String testCode);
    void saveTypeInfo(List<TypeInfoDto> typeInfoDtoList, String testCode);
    List<QuestionDto> findQuestionInfo(String testCode);
    List<TypeInfoDto> findTypeInfo(String testCode);
    List<String> getEssentialTypeList(String testCode);
    void disableTest(String testCode);
}
