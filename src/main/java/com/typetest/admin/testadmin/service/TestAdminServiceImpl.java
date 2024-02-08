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
    public TestInfoDto saveTestInfo(TestInfoDto testInfoDto) {
        testCodeInfoRepository.save(testInfoDto.toEntity());
        return testInfoDto;
    }

    @Override
    public List<TypeIndicatorDto> findIndicatorInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                    .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
            List<TypeIndicator> indicatorList = typeIndicatorRepository.findByTestCode(testCodeInfo);
            List<TypeIndicatorDto> indicatorDtoList = indicatorList.stream()
                    .map(TypeIndicatorDto::new)
                    .collect(Collectors.toList());
            return indicatorDtoList;
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
            List<QuestionDto> questionDtoList = questionList.stream()
                    .map(QuestionDto::new)
                    .collect(Collectors.toList());
            return questionDtoList;
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
                List<TypeInfoDto> typeInfoDtoList = typeInfoList.stream()
                        .map(TypeInfoDto::new)
                        .collect(Collectors.toList());
                return typeInfoDtoList;
        }
    }

    @Override
    @Transactional
    public ResultCode saveIndicatorInfo(List<TypeIndicatorDto> indicatorDtoList, String testCode) {
        ResultCode resultCode = ResultCode.EXIST_INDICATOR_TEST;
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));

        for (TypeIndicatorDto typeIndicatorDto : indicatorDtoList) {
            TypeIndicator indicator = null;
            // 삭제된 데이터가 아니라면..
            if(typeIndicatorDto.getDeleted() != 1) {
                // 신규 엔티티이거나 업데이트된 엔티티면 save
                if(typeIndicatorDto.isNewEntity() || typeIndicatorDto.getUpdated() == 1) {
                    indicator = new TypeIndicator(testCodeInfo, typeIndicatorDto);
                    typeIndicatorRepository.save(indicator);
                }

                // 자식 리스트 순회
                for (IndicatorSettingDto indicatorSettingDto : typeIndicatorDto.getIndicatorSettings()) {
                    IndicatorSetting indicatorSetting = null;
                    // 삭제된 데이터가 아니라면..
                    if(indicatorSettingDto.getDeleted() != 1) {
                        // 신규 엔티티이거나 업데이트된 엔티티면 save
                        if(indicatorSettingDto.isNewEntity() || indicatorSettingDto.getUpdated() == 1) {
                            if(indicator == null) {
                                indicator = new TypeIndicator(testCodeInfo, typeIndicatorDto);
                            }
                            indicatorSetting = new IndicatorSetting(indicator, testCodeInfo, indicatorSettingDto);
                            indicatorSettingRepository.save(indicatorSetting);
                        }
                    } else {
                        // 삭제 데이터면 신규 엔티티여부 확인 하여 아닐시 삭제
                        if(!indicatorSettingDto.isNewEntity()) {
                            indicatorSettingRepository.deleteById(indicatorSettingDto.getId());
                        }
                    }
                }
            } else {
                // 삭제 데이터면 신규 엔티티여부 확인 하여 아닐시 삭제
                if(!typeIndicatorDto.isNewEntity()) {
                    typeIndicatorRepository.deleteById(typeIndicatorDto.getId());
                    resultCode = ResultCode.NONE_INDICATOR_TEST;
                }
            }
        }
        return resultCode;
    }

    @Override
    @Transactional
    public int saveQuestionInfo(List<QuestionDto> questionDtoList, String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        for (QuestionDto questionDto : questionDtoList) {
            PersonalityQuestion question = null;
            // 삭제된 데이터가 아니라면..
            if (questionDto.getDeleted() != 1) {
                // 신규 엔티티 혹은 업데이트된 엔티티면 save
                if (questionDto.isNewEntity() || questionDto.getUpdated() == 1) {
                    question = new PersonalityQuestion(questionDto, testCodeInfo);
                    personalityQuestionRepository.save(question);
                }

                // 자식 리스트 순회
                for (AnswerDto answerDto : questionDto.getAnswerList()) {
                    PersonalityAnswer answer = null;
                    // 삭제한 데이터가 아니면
                    if(answerDto.getDeleted() != 1) {
                        // 신규 데이터면 엔티티 새로 만들어 주기
                        if (answerDto.isNewEntity() || answerDto.getUpdated() == 1) {
                            TypeIndicator indicator = typeIndicatorRepository.findById(answerDto.getTypeIndicatorId())
                                    .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, answerDto.getTypeIndicatorId().toString()));
                            if(question == null) {
                                question = new PersonalityQuestion(questionDto, testCodeInfo);
                            }
                            answerDto.setTypeIndicator(indicator);
                            answer = new PersonalityAnswer(answerDto, question, testCodeInfo);
                            personalityAnswerRepository.save(answer);
                        }
                    } else {
                        // 삭제데이터이면서 신규데이터가 아니라면 삭제
                        if(!answerDto.isNewEntity()) {
                            personalityAnswerRepository.deleteById(answerDto.getId());
                        }
                    }
                }
            } else {
                // 삭제 데이터면 신규 엔티티여부 확인 하여 아닐시 삭제
                if(!questionDto.isNewEntity()) {
                    personalityQuestionRepository.deleteById(questionDto.getId());
                }
            }

        }
        return 1;
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
     * @return
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
    @Transactional
    public int saveTypeInfo(List<TypeInfoDto> typeInfoDtoList, String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        for (TypeInfoDto typeInfoDto : typeInfoDtoList) {
            TypeInfo typeInfo = null;
            // 삭제된 데이터가 아니라면..
            if(typeInfoDto.getDeleted() != 1) {
                // 신규 엔티티이거나 업데이트된 엔티티면 save
                if(typeInfoDto.isNewEntity() || typeInfoDto.getUpdated() == 1) {
                    typeInfo = new TypeInfo(testCodeInfo, typeInfoDto);
                    typeInfoRepository.save(typeInfo);
                }

                // 1:1 관계인 typeRelation 처리
                TypeRelationDto typeRelationDto = typeInfoDto.getTypeRelation();
                if (typeRelationDto.getUpdated() == 1) {
                    if (typeRelationDto.isNewEntity()) {
                        if(typeInfo == null) {
                            typeInfo = new TypeInfo(testCodeInfo, typeInfoDto);
                        }
                        typeRelationDto.setTypeInfoId(typeInfo.getId());
                        typeRelationRepository.insertTypeRelation(typeRelationDto);
                    } else {
                        typeRelationRepository.updateTypeRelation(typeRelationDto);
                    }
                }

                // 자식 리스트 순회
                for (TypeImageDto typeImageDto : typeInfoDto.getTypeImageList()) {
                    TypeImage typeImage = null;
                    // 삭제된 데이터가 아니라면..
                    if(typeImageDto.getDeleted() != 1) {
                        // 신규 엔티티면 새로 만들기
                        if(typeImageDto.isNewEntity() || typeImageDto.getUpdated() == 1) {
                            if(typeInfo == null) {
                                typeInfo = new TypeInfo(testCodeInfo, typeInfoDto);
                            }
                            typeImage = new TypeImage(typeInfo, typeImageDto);
                            typeImageRepository.save(typeImage);
                        }

                    } else {
                        // 삭제 데이터면 신규 엔티티여부 확인 하여 아닐시 삭제
                        if(!typeImageDto.isNewEntity()) {
                            typeImageRepository.deleteById(typeImageDto.getId());
                        }
                    }
                }

                // 자식 리스트 순회
                for (TypeDescriptionDto typeDescriptionDto : typeInfoDto.getTypeDescriptionList()) {
                    TypeDescription typeDescription = null;
                    // 삭제된 데이터가 아니라면..
                    if(typeDescriptionDto.getDeleted() != 1) {
                        // 신규 엔티티면 새로 만들기
                        if(typeDescriptionDto.isNewEntity() || typeDescriptionDto.getUpdated() == 1) {
                            if(typeInfo == null) {
                                typeInfo = new TypeInfo(testCodeInfo, typeInfoDto);
                            }
                            typeDescription = new TypeDescription(typeInfo, typeDescriptionDto);
                            typeDescriptionRepository.save(typeDescription);
                        }

                    } else {
                        // 삭제 데이터면 신규 엔티티여부 확인 하여 아닐시 삭제
                        if(!typeDescriptionDto.isNewEntity()) {
                            typeDescriptionRepository.deleteById(typeDescriptionDto.getId());
                        }
                    }
                }

            } else {
                // 삭제 데이터면 신규 엔티티여부 확인 하여 아닐시 삭제
                if(!typeInfoDto.isNewEntity()) {
                    typeInfoRepository.deleteById(typeInfoDto.getId());
                }
            }

        }
        return 1;
    }

    @Override
    public void disableTest(String testCode) {
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        testCodeInfoRepository.disableTest(testCodeInfo);
    }
}
