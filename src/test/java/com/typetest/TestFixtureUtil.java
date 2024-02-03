package com.typetest;

import com.typetest.personalities.data.AnswerType;
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

    public static TestCodeInfo setDefaultTestCodeInfo(TestCodeInfoRepository testCodeInfoRepository) {
        TestCodeInfo testCodeInfo = TestCodeInfo.builder()
                .testCode("EXAMTEST")
                .testName("EXAM예제")
                .answerType(AnswerType.EXAM)
                .image("https://d2k6w3n3qf94c4.cloudfront.net/media/banners/images/07_mmangsi.png")
                .description("These Sass loops aren’t limited to color maps, either. You can also generate responsive variations of your components.")
                .active(1)
                .thumbnailDesc("테스트용 테스트! 검사결과가 정상적으로 나오지 않을 수 있습니다.")
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
        TypeInfo typeInfo = new TypeInfo(testCodeInfo, "타입코드", "타입명");
        return typeInfoRepository.save(typeInfo);
    }

    public static TestResult setDefaultTestResult(
            TestResultRepository testResultRepository,
            TestCodeInfo testCodeInfo,
            User user,
            TypeInfo typeInfo
    ) {
        TestResult testResult = new TestResult(user, testCodeInfo, typeInfo);
        return testResultRepository.save(testResult);
    }

}
