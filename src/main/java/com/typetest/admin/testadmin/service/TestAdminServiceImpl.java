package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.*;
import com.typetest.exception.NotFoundEntityException;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
    public List<QuestionDto> findQuestionInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
            if(testCodeInfo.isPresent()) {
                List<PersonalityQuestion> questionList = personalityQuestionRepository.findByTestCode(testCodeInfo.get());
                List<QuestionDto> questionDtoList = questionList.stream().map(QuestionDto::new).collect(Collectors.toList());
                return questionDtoList;
            } else {
                throw new NotFoundEntityException("[" + testCode + "] 에 해당하는 테스트 정보를 찾을 수 없습니다.");
            }
        }
    }

    @Override
    public List<TypeInfoDto> findTypeInfo(String testCode) {
        if(testCode.equals("NEW")) {
            return new ArrayList<>();
        } else {
            Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
            if(testCodeInfo.isPresent()) {
                List<TypeInfo> typeInfoList = typeInfoRepository.findByTestCode(testCodeInfo.get());
                List<TypeInfoDto> typeInfoDtoList = typeInfoList.stream().map(TypeInfoDto::new).collect(Collectors.toList());
                return typeInfoDtoList;
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
                    if(typeIndicatorDto.emptyValueCheck()) {
                        typeIndicatorRepository.delete(indicator);
                        indicator = null;
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
                if(typeIndicatorDto.emptyValueCheck()) {
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
                            if(indicatorSettingDto.emptyValueCheck()) {
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
                        if(indicatorSettingDto.emptyValueCheck()) {
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

    @Override
    public int saveQuestionInfo(List<QuestionDto> questionDtoList, String testCode) {
        Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
        for (QuestionDto questionDto : questionDtoList) {
            PersonalityQuestion question = null;
            // 삭제된 데이터가 아니라면..
            if (questionDto.getDeleted() != 1) {
                // 신규 엔티티면 새로 만들기
                if (questionDto.isNewEntity()) {
                    question = new PersonalityQuestion(questionDto, testCodeInfo.get());
                    personalityQuestionRepository.save(question);
                } else {
                    // 신규 엔티티 아니고 업데이트된 내용 있으면 업데이트
                    if(questionDto.getUpdated() == 1) {
                        question = new PersonalityQuestion(questionDto, testCodeInfo.get());
                        personalityQuestionRepository.save(question);
                    }
                }

                // 답변 리스트 순회
                for (AnswerDto answerDto : questionDto.getAnswerList()) {
                    PersonalityAnswer answer = null;
                    // 삭제한 데이터가 아니면
                    if(answerDto.getDeleted() != 1) {
                        // 신규 데이터면 엔티티 새로 만들어 주기
                        if (answerDto.isNewEntity()) {
                            Optional<TypeIndicator> indicator = typeIndicatorRepository.findById(answerDto.getTypeIndicatorId());
                            if(indicator.isPresent()) {
                                if(question == null) {
                                    question = new PersonalityQuestion(questionDto, testCodeInfo.get());
                                }
                                answerDto.setTypeIndicator(indicator.get());
                                answer = new PersonalityAnswer(answerDto, question, testCodeInfo.get());
                                personalityAnswerRepository.save(answer);
                            } else {
                                throw new NotFoundEntityException();
                            }
                        } else {
                            // 신규엔티티 아니고 업데이트된 데이터 있으면 업데이트
                            if(answerDto.getUpdated() == 1) {
                                Optional<TypeIndicator> findIndicator = typeIndicatorRepository.findById(answerDto.getTypeIndicatorId());
                                if (findIndicator.isPresent()) {
                                    answerDto.setTypeIndicator(findIndicator.get());
                                    answer = new PersonalityAnswer(answerDto, question, testCodeInfo.get());
                                    personalityAnswerRepository.save(answer);
                                } else {
                                    throw new NotFoundEntityException();
                                }
                            }
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
        Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
        if (testCodeInfo.isPresent()) {
            HashMap<Integer, List<String>> indicatorMap = new HashMap<>();
            List<IndicatorSetting> indiSetList = indicatorSettingRepository.findByTestCode(testCodeInfo.get());
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
        } else {
            throw new NotFoundEntityException("[" + testCode + "] 에 해당하는 테스트 정보를 찾을 수 없습니다.");
        }
    }

    /***
     *  도출될 수 있는 모든 유형을 구하는 백트래킹 조합 알고리즘
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
}
