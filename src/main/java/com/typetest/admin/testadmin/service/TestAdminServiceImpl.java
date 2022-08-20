package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.IndicatorInfoDto;
import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.exception.NotFoundEntityException;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.repository.IndicatorSettingRepositoryRepository;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestAdminServiceImpl implements TestAdminService {

    private final TestCodeInfoRepository testCodeInfoRepository;
    private final IndicatorSettingRepositoryRepository indicatorSettingRepository;

    @Override
    public TestInfoDto createTestInfoDto(String testCode) {
        if(testCode.equals("NEW")) {
            return new TestInfoDto();
        } else {
            Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
            if(testCodeInfo.isPresent()) {
                return new TestInfoDto(testCodeInfo.get());
            } else {
                throw new NotFoundEntityException("[" + testCode + "] 에 해당하는 테스트 정보를 찾을 수 없습니다.");
            }
        }
    }

    @Override
    public List<TestCodeInfo> findAllTestInfo() {
        return testCodeInfoRepository.findAll();
    }

    @Override
    public TestInfoDto saveTestInfo(TestInfoDto testInfoDto) {
        TestCodeInfo saveResult = testCodeInfoRepository.save(new TestCodeInfo(testInfoDto));
        return testInfoDto;
    }

    @Override
    public List<IndicatorInfoDto> findIndicatorInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
            if(testCodeInfo.isPresent()) {
                return indicatorSettingRepository.getIndicatorInfoList(testCodeInfo.get());
            } else {
                throw new NotFoundEntityException("[" + testCode + "] 에 해당하는 테스트 정보를 찾을 수 없습니다.");
            }
        }
    }
}
