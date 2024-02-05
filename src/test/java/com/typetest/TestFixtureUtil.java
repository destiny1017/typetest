package com.typetest;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.*;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class TestFixtureUtil {

    public static User setDefaultUser(UserRepository userRepository) {
        // 테스트용 사용자 데이터
        User user = User.builder()
                .name("김대호")
                .email("eogh6428@gmail.com")
                .picture("https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c")
                .role(Role.ADMIN)
                .nickname("디앙")
                .build();
        return userRepository.save(user);
    }

    public static TestCodeInfo setDefaultExamTestCodeInfo(TestCodeInfoRepository testCodeInfoRepository) {
        TestCodeInfo testCodeInfo = TestCodeInfo.builder()
                .testCode("EXAMTEST")
                .testName("EXAM예제")
                .answerType(AnswerType.EXAM)
                .active(1)
                .build();
        return testCodeInfoRepository.save(testCodeInfo);
    }

    public static TestCodeInfo setDefaultCardTestCodeInfo(TestCodeInfoRepository testCodeInfoRepository) {
        TestCodeInfo testCodeInfo = TestCodeInfo.builder()
                .testCode("CARDTEST")
                .testName("CARD예제")
                .answerType(AnswerType.CARD)
                .active(1)
                .build();
        return testCodeInfoRepository.save(testCodeInfo);
    }

    public static List<PersonalityQuestion> setDefaultPersonalityQuestion(
            PersonalityQuestionRepository personalityQuestionRepository,
            TestCodeInfo testCodeInfo
    ) {
        List<PersonalityQuestion> questionList = new ArrayList<>();
        questionList.add(new PersonalityQuestion(testCodeInfo, "질문1", 1));
        questionList.add(new PersonalityQuestion(testCodeInfo, "질문2", 2));
        questionList.add(new PersonalityQuestion(testCodeInfo, "질문3", 3));
        questionList.add(new PersonalityQuestion(testCodeInfo, "질문4", 4));
        questionList.add(new PersonalityQuestion(testCodeInfo, "질문5", 5));
        questionList.add(new PersonalityQuestion(testCodeInfo, "질문6", 6));
        return personalityQuestionRepository.saveAll(questionList);
    }

//    public static PersonalityAnswer setDefaultPersonalityAnswer(
//            PersonalityAnswerRepository personalityAnswerRepository,
//            TestCodeInfo testCodeInfo,
//            TypeIndicator typeIndicator
//    ) {
//        PersonalityAnswer.builder()
//                .testCode(testCodeInfo)
//                .typeIndicator(typeIndicator)
//                .answer("답변1")
//                .tendency(Tendency.A)
//                .build();
//    }

    public static PersonalityQuestion setAnswerToQuestion(
            PersonalityQuestionRepository personalityQuestionRepository,
            PersonalityAnswerRepository personalityAnswerRepository,
            PersonalityQuestion question,
            List<PersonalityAnswer> answerList
    ) {
        answerList.forEach(question::addAnswer);
        personalityAnswerRepository.saveAll(answerList);
        return personalityQuestionRepository.save(question);
    }

    public static List<PersonalityAnswer> setDefaultAnswerList(
            PersonalityAnswerRepository personalityAnswerRepository,
            TestCodeInfo testCodeInfo,
            TypeIndicator typeIndicator
    ) {
        List<PersonalityAnswer> answerList = new ArrayList<>();
        answerList.add(PersonalityAnswer.builder()
                .testCode(testCodeInfo)
                .answer("응답1")
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(typeIndicator)
                .build());
        answerList.add(PersonalityAnswer.builder()
                .testCode(testCodeInfo)
                .answer("응답2")
                .point(2)
                .tendency(Tendency.C)
                .typeIndicator(typeIndicator)
                .build());
        return personalityAnswerRepository.saveAll(answerList);
    }

    public static List<TypeIndicator> setDefaultTypeIndicator(
            TypeIndicatorRepository typeIndicatorRepository,
            TestCodeInfo testCodeInfo
    ) {
        List<TypeIndicator> typeIndicatorList = new ArrayList<>();
        typeIndicatorList.add(new TypeIndicator(testCodeInfo, 1, "A지표"));
        typeIndicatorList.add(new TypeIndicator(testCodeInfo, 2, "B지표"));
        typeIndicatorList.add(new TypeIndicator(testCodeInfo, 3, "C지표"));
        return typeIndicatorRepository.saveAll(typeIndicatorList);
    }

    public static TypeInfo setDefaultTypeInfo(
            TypeInfoRepository typeInfoRepository,
            TestCodeInfo testCodeInfo
    ) {
        TypeInfo typeInfo = TypeInfo.builder()
                .testCode(testCodeInfo)
                .typeCode("타입코드")
                .typeName("타입명")
                .build();
        return typeInfoRepository.save(typeInfo);
    }

    public static TestResult setDefaultTestResult(
            TestResultRepository testResultRepository,
            TestCodeInfo testCodeInfo,
            User user,
            TypeInfo typeInfo
    ) {
        TestResult testResult = TestResult.builder()
                .user(user)
                .testCode(testCodeInfo)
                .typeInfo(typeInfo)
                .build();
        return testResultRepository.save(testResult);
    }

    public static TestResultDetail setTestResultDetail(
            TestResultDetailRepository testResultDetailRepository,
            TestResult testResult,
            User user,
            TestCodeInfo testCodeInfo,
            PersonalityAnswer personalityAnswer,
            Integer num
    ) {
        TestResultDetail testResultDetail = TestResultDetail.builder()
                .testCode(testCodeInfo)
                .testResult(testResult)
                .user(user)
                .personalityAnswer(personalityAnswer)
                .num(num)
                .build();
        return testResultDetailRepository.save(testResultDetail);
    }

}
