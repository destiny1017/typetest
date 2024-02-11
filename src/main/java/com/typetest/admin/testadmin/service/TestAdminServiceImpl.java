package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.*;
import com.typetest.constant.ErrorCode;
import com.typetest.constant.ResultCode;
import com.typetest.exception.TypetestException;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestAdminServiceImpl implements TestAdminService {

    private final TestCodeInfoRepository testCodeInfoRepository;
    private final IndicatorSettingRepository indicatorSettingRepository;
    private final TypeIndicatorRepository typeIndicatorRepository;
    private final PersonalityQuestionRepository personalityQuestionRepository;
    private final PersonalityAnswerRepository personalityAnswerRepository;
    private final TypeInfoRepository typeInfoRepository;
    private final TypeImageRepository typeImageRepository;
    private final TypeDescriptionRepository typeDescriptionRepository;
    private final TypeRelationRepository typeRelationRepository;

    @Override
    public TestInfoDto createTestInfoDto(String testCode) {
        if(testCode.equals("NEW")) {
            return new TestInfoDto();
        } else {
            TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                    .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
            return new TestInfoDto(testCodeInfo);
        }
    }

    @Override
    public List<TestCodeInfo> findAllTestInfo() {
        return testCodeInfoRepository.findAll();
    }

    @Override
    public void saveTestInfo(TestInfoDto testInfoDto) {
        testCodeInfoRepository.save(testInfoDto.toEntity());
    }

    @Override
    public List<TypeIndicatorDto> findIndicatorInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                    .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
            List<TypeIndicator> indicatorList = typeIndicatorRepository.findByTestCode(testCodeInfo);
            return indicatorList.stream()
                    .map(TypeIndicatorDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<QuestionDto> findQuestionInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                    .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
            List<PersonalityQuestion> questionList = personalityQuestionRepository.findByTestCode(testCodeInfo);
            return questionList.stream()
                    .map(QuestionDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<TypeInfoDto> findTypeInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                    .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
                List<TypeInfo> typeInfoList = typeInfoRepository.findByTestCode(testCodeInfo);
                return typeInfoList.stream()
                        .map(TypeInfoDto::new)
                        .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public ResultCode saveIndicatorInfo(List<TypeIndicatorDto> indicatorDtoList, String testCode) {
        ResultCode resultCode = ResultCode.EXIST_INDICATOR_TEST;
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));

        for (TypeIndicatorDto typeIndicatorDto : indicatorDtoList) {
            if(typeIndicatorDto.isNewOrUpdatedEntity()) {
                TypeIndicator indicator = typeIndicatorDto.toEntity(testCodeInfo);
                indicator = typeIndicatorRepository.save(indicator);
                saveIndicatorSettings(testCodeInfo, indicator, typeIndicatorDto.getIndicatorSettings());
            } else if(typeIndicatorDto.isDeletedEntity()) {
                typeIndicatorRepository.deleteById(typeIndicatorDto.getId());
                resultCode = ResultCode.NONE_INDICATOR_TEST;
            }
        }
        return resultCode;
    }

    private void saveIndicatorSettings(TestCodeInfo testCodeInfo, TypeIndicator indicator, List<IndicatorSettingDto> indicatorSettings) {
        for (IndicatorSettingDto indicatorSettingDto : indicatorSettings) {
            if(indicatorSettingDto.isNewOrUpdatedEntity()) {
                IndicatorSetting indicatorSetting = indicatorSettingDto.toEntity(indicator, testCodeInfo);
                indicatorSettingRepository.save(indicatorSetting);
            } else if(indicatorSettingDto.isDeletedEntity()) {
                indicatorSettingRepository.deleteById(indicatorSettingDto.getId());
            }
        }
    }

    @Override
    @Transactional
    public void saveQuestionInfo(List<QuestionDto> questionDtoList, String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        for (QuestionDto questionDto : questionDtoList) {
            if (questionDto.isNewOrUpdatedEntity()) {
                PersonalityQuestion question = questionDto.toEntity(testCodeInfo);
                personalityQuestionRepository.save(question);
                saveAnswerList(testCodeInfo, question, questionDto.getAnswerList());
            } else if(questionDto.isDeletedEntity()) {
                personalityQuestionRepository.deleteById(questionDto.getId());
            }
        }
    }

    private void saveAnswerList(TestCodeInfo testCodeInfo, PersonalityQuestion question, List<AnswerDto> answerList) {
        for (AnswerDto answerDto : answerList) {
            if(answerDto.isNewOrUpdatedEntity()) {
                TypeIndicator indicator = typeIndicatorRepository.findById(answerDto.getTypeIndicatorId())
                        .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, answerDto.getTypeIndicatorId().toString()));
                PersonalityAnswer answer = answerDto.toEntity(testCodeInfo, question, indicator);
                personalityAnswerRepository.save(answer);
            } else if(answerDto.isDeletedEntity()) {
                personalityAnswerRepository.deleteById(answerDto.getId());
            }
        }
    }

    @Override
    @Transactional
    public void saveTypeInfo(List<TypeInfoDto> typeInfoDtoList, String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        for (TypeInfoDto typeInfoDto : typeInfoDtoList) {
            if(typeInfoDto.isNewOrUpdatedEntity()) {
                TypeInfo typeInfo = typeInfoDto.toEntity(testCodeInfo);

                TypeRelationDto typeRelationDto = typeInfoDto.getTypeRelation();
                typeRelationDto.setTypeInfoId(typeInfo.getId());
                typeInfo.updateTypeRelation(typeRelationDto.toEntity());
                typeInfoRepository.save(typeInfo);

                saveTypeImages(typeInfoDto.getTypeImageList(), typeInfo);
                saveTypeDescriptions(typeInfoDto.getTypeDescriptionList(), typeInfo);
            } else if(typeInfoDto.isDeletedEntity()) {
                typeInfoRepository.deleteById(typeInfoDto.getId());
            }
        }
    }

    private void saveTypeImages(List<TypeImageDto> typeImageList, TypeInfo typeInfo) {
        for (TypeImageDto typeImageDto : typeImageList) {
            if(typeImageDto.isNewOrUpdatedEntity()) {
                TypeImage typeImage = typeImageDto.toEntity(typeInfo);
                typeImageRepository.save(typeImage);
            } else if(typeImageDto.isDeletedEntity()) {
                typeImageRepository.deleteById(typeImageDto.getId());
            }
        }
    }

    private void saveTypeDescriptions(List<TypeDescriptionDto> typeDescriptionList, TypeInfo typeInfo) {
        for (TypeDescriptionDto typeDescriptionDto : typeDescriptionList) {
            if(typeDescriptionDto.isNewOrUpdatedEntity()) {
                TypeDescription typeDescription = typeDescriptionDto.toEntity(typeInfo);
                typeDescriptionRepository.save(typeDescription);
            } else if(typeDescriptionDto.isDeletedEntity()) {
                typeDescriptionRepository.deleteById(typeDescriptionDto.getId());
            }
        }
    }

    @Override
    public List<String> getEssentialTypeList(String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        HashMap<Integer, List<String>> indicatorMap = new HashMap<>();
        List<IndicatorSetting> indiSetList = indicatorSettingRepository.findByTestCode(testCodeInfo);
        for (IndicatorSetting indicatorSetting : indiSetList) {
            Integer indiNum = indicatorSetting.getTypeIndicator().getIndicatorNum() - 1; // 0인덱스부터 조합하기 위해 1 빼줌
            indicatorMap // 지표 번호(순서)를 key로, 가능한 결과값을 리스트로 생성하여 value에 추가해주기
                    .getOrDefault(indiNum, indicatorMap.putIfAbsent(indiNum, new ArrayList<>()))
                    .add(indicatorSetting.getResult());
        }
        int typeLength = indicatorMap.size(); // 결과유형 길이
        String[] tmpArr = new String[typeLength];
        List<String> allCaseOfType = new ArrayList<>();

        return getAllCaseOfType(0, typeLength, indicatorMap, tmpArr, allCaseOfType);
    }

    /***
     *  도출될 수 있는 모든 유형을 구하는 백트래킹 중복순열 알고리즘
     * @param depth     각 사이클마다 할당되는 depth(index)
     * @param m         결과값 길이
     * @param valueMap  depth(indicatorNum)별 다르게 적용되는 조합지표 정보
     * @param tmpArr    조합가능한 유형 담아둘 배열
     * @param result    결과 담아서 보내줄 리스트
     * @return List<String>
     */
    public List<String> getAllCaseOfType(int depth, int m, HashMap<Integer, List<String>> valueMap,
                                      String[] tmpArr, List<String> result) {
        if(depth == m) {
            result.add(String.join("", tmpArr));
            return null;
        }

        for (int i = 0; i < valueMap.get(depth).size(); i++) {
            tmpArr[depth] = valueMap.get(depth).get(i);
            getAllCaseOfType(depth+1, m, valueMap, tmpArr, result);
        }
        return result;
    }

    @Override
    public void disableTest(String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        testCodeInfoRepository.disableTest(testCodeInfo);
    }
}
