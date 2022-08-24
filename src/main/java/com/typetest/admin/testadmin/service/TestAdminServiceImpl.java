package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.IndicatorSettingDto;
import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.admin.testadmin.data.TypeIndicatorDto;
import com.typetest.exception.NotFoundEntityException;
import com.typetest.personalities.domain.IndicatorSetting;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import com.typetest.personalities.repository.IndicatorSettingRepositoryRepository;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import com.typetest.personalities.repository.TypeIndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestAdminServiceImpl implements TestAdminService {

    private final TestCodeInfoRepository testCodeInfoRepository;
    private final IndicatorSettingRepositoryRepository indicatorSettingRepository;
    private final TypeIndicatorRepository typeIndicatorRepository;

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
    public List<TypeIndicatorDto> findIndicatorInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
            if(testCodeInfo.isPresent()) {
                List<TypeIndicator> indicatorList = typeIndicatorRepository.findByTestCode(testCodeInfo.get());
                List<TypeIndicatorDto> indicatorDtoList = indicatorList.stream().map(TypeIndicatorDto::new).collect(Collectors.toList());
                return indicatorDtoList;
            } else {
                throw new NotFoundEntityException("[" + testCode + "] 에 해당하는 테스트 정보를 찾을 수 없습니다.");
            }
        }
    }

    @Override
    public int saveIndicatorInfo(List<TypeIndicatorDto> indicatorDtoList, String testCode) {
        Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
        for (TypeIndicatorDto typeIndicatorDto : indicatorDtoList) {
            TypeIndicator indicator;
            if(typeIndicatorDto.getId() != null) {
                Optional<TypeIndicator> findTypeIndicator = typeIndicatorRepository.findById(typeIndicatorDto.getId());
                if(findTypeIndicator.isPresent()) {
                    indicator = findTypeIndicator.get();
                    // 넘버와 네임에 입력된 값 없으면 삭제
                    if(typeIndicatorDto.getIndicatorNum() == null
                            && typeIndicatorDto.getIndicatorName().length() == 0) {
                        typeIndicatorRepository.delete(indicator);
                    } else { // 값 있으면 기존 값과 같은지 체크 후 다르면 업데이트
                        if(!indicator.checkSameValue(typeIndicatorDto)) {
                            indicator.updateIndicatorInfo(typeIndicatorDto);
                            typeIndicatorRepository.save(indicator);
                        }
                    }
                } else {
                    throw new NotFoundEntityException();
                }
            } else {
                if(typeIndicatorDto.getIndicatorNum() == null
                        && typeIndicatorDto.getIndicatorName().length() == 0) {
                    // id값도 value값도 없는 데이터는 무시. 신규생성 눌렀다 취소한 엔티티임
                    indicator = null;
                } else {
                    // DTO에 id값 없고 value값은 있으면, 즉 신규 엔티티면 새로 만들기
                    indicator = new TypeIndicator(
                            testCodeInfo.get(),
                            typeIndicatorDto.getIndicatorNum(),
                            typeIndicatorDto.getIndicatorName());

                    typeIndicatorRepository.save(indicator);
                }
            }

            if(indicator != null) {
                for (IndicatorSettingDto indicatorSettingDto : typeIndicatorDto.getIndicatorSettings()) {
                    if(indicatorSettingDto.getId() != null) {
                        Optional<IndicatorSetting> findSetting = indicatorSettingRepository.findById(indicatorSettingDto.getId());
                        if (findSetting.isPresent()) {
                            IndicatorSetting indicatorSetting = findSetting.get();
                            // 포인트와 결과에 입력된 값 없으면 삭제
                            if(indicatorSettingDto.getCuttingPoint() == null
                                    && indicatorSettingDto.getResult().length() == 0) {
                                indicatorSettingRepository.delete(indicatorSetting);
                            } else { // 값 있으면 기존 값과 같은지 체크 후 다르면 업데이트
                                if(!indicatorSetting.checkSameValue(indicatorSettingDto)) {
                                    indicatorSetting.updateIndicatorSetting(indicatorSettingDto);
                                    indicatorSettingRepository.save(indicatorSetting);
                                }
                            }
                        } else {
                            throw new NotFoundEntityException();
                        }
                    } else {
                        if(indicatorSettingDto.getCuttingPoint() == null
                                && indicatorSettingDto.getResult().length() == 0) {
                            // id값도 value값도 없는 데이터는 무시. 신규생성 눌렀다 취소한 엔티티임
                        } else {
                            // Dto에 ID값 없고 value값 있는 신규 엔티티면 생성
                            IndicatorSetting indicatorSetting = new IndicatorSetting(
                                    indicator,
                                    testCodeInfo.get(),
                                    indicatorSettingDto.getCuttingPoint(),
                                    indicatorSettingDto.getResult());
                            indicatorSettingRepository.save(indicatorSetting);
                        }
                    }
                }
            }

        }
        return 1;
    }
}
