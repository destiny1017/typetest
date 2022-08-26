package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.*;
import com.typetest.exception.NotFoundEntityException;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.*;
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
            PersonalityQuestion question;
            if (questionDto.getId() != null) {
                Optional<PersonalityQuestion> findQuestion = personalityQuestionRepository.findById(questionDto.getId());
                if (findQuestion.isPresent()) {
                    question = findQuestion.get();
                    // 질문번호와 질문내용 입력된 거 없으면 삭제
                    if (questionDto.emptyValueCheck()) {
                        personalityQuestionRepository.delete(question);
                        question = null;
                    } else { // 값 있으면 기존 값과 같은지 체크 후 다르면 업데이트
                        if (!question.checkSameValue(questionDto)) {
                            question.updateQuestionInfo(questionDto);
                            personalityQuestionRepository.save(question);
                        }
                    }
                } else {
                    throw new NotFoundEntityException();
                }
            } else {
                if (questionDto.emptyValueCheck()) {
                    // id값도 value값도 없는 데이터는 무시. 신규생성 눌렀다 취소한 엔티티임
                    question = null;
                } else {
                    // DTO에 id값 없고 value값은 있으면, 즉 신규 엔티티면 새로 만들기
                    question = new PersonalityQuestion(
                            testCodeInfo.get(),
                            questionDto.getQuestion(),
                            questionDto.getNum(),
                            questionDto.getQuestionImage());

                    personalityQuestionRepository.save(question);
                }
            }

            if (question != null) {
                for (AnswerDto answerDto : questionDto.getAnswerList()) {
                    if (answerDto.getId() != null) {
                        Optional<PersonalityAnswer> findAnswer = personalityAnswerRepository.findById(answerDto.getId());
                        if (findAnswer.isPresent()) {
                            PersonalityAnswer answer = findAnswer.get();
                            // 입력값 없으면 삭제
                            if (answerDto.emptyValueCheck()) {
                                personalityAnswerRepository.delete(answer);
                            } else { // 값 있으면 기존 값과 같은지 체크 후 다르면 업데이트
                                if (!answer.checkSameValue(answerDto)) {
                                    Optional<TypeIndicator> indicator = typeIndicatorRepository.findById(answerDto.getTypeIndicatorId());
                                    if (indicator.isPresent()) {
                                        answerDto.setTypeIndicator(indicator.get());
                                        answer.updateAnswerInfo(answerDto);
                                        personalityAnswerRepository.save(answer);
                                    } else {
                                        throw new NotFoundEntityException();
                                    }
                                }
                            }
                        } else {
                            throw new NotFoundEntityException();
                        }
                    } else {
                        if (answerDto.emptyValueCheck()) {
                            // id값도 value값도 없는 데이터는 무시. 신규생성 눌렀다 취소한 엔티티임
                        } else {
                            Optional<TypeIndicator> indicator = typeIndicatorRepository.findById(answerDto.getTypeIndicatorId());
                            if(indicator.isPresent()) {
                                PersonalityAnswer answer = new PersonalityAnswer(
                                        question,
                                        testCodeInfo.get(),
                                        answerDto.getAnswer(),
                                        answerDto.getPoint(),
                                        answerDto.getTendency(),
                                        answerDto.getAnswerImage(),
                                        indicator.get()
                                );
                                personalityAnswerRepository.save(answer);
                            } else {
                                throw new NotFoundEntityException();
                            }
                        }
                    }
                }
            }

        }
        return 1;
    }
}
