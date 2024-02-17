package com.typetest.mypage.service;

import com.typetest.IntegrationTestSupport;
import com.typetest.common.exception.TypetestException;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.data.UserTendencyInfo;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.*;
import com.typetest.user.domain.User;
import com.typetest.mypage.data.TypeInfoData;
import com.typetest.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;

import static com.typetest.TestFixtureUtil.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
class MyPageServiceTest extends IntegrationTestSupport {

    @Autowired
    MyPageService myPageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestCodeInfoRepository testCodeInfoRepository;

    @Autowired
    TypeInfoRepository typeInfoRepository;

    @Autowired
    TestResultRepository testResultRepository;

    @Autowired
    PersonalityAnswerRepository personalityAnswerRepository;

    @Autowired
    TestResultDetailRepository testResultDetailRepository;

    @Autowired
    TypeIndicatorRepository typeIndicatorRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("테스트 결과 조회시 동일한 테스트에 대한 결과가 여러개 있으면 가장 최근의 결과가 리턴된다")
    void getUserTypeInfoTest() throws Exception {
        // given
        User user = setDefaultUser(userRepository);
        TestCodeInfo testCodeInfo = setDefaultExamTestCodeInfo(testCodeInfoRepository);
        TypeInfo typeInfoOld = setDefaultTypeInfo(typeInfoRepository, testCodeInfo);
        TestResult testResultOld = setDefaultTestResult(testResultRepository, testCodeInfo, user, typeInfoOld);
        TypeInfo typeInfoRecent = TypeInfo.builder()
                .testCode(testCodeInfo)
                .typeCode("타입코드2")
                .typeName("타임명2")
                .build();
        typeInfoRecent= typeInfoRepository.save(typeInfoRecent);
        TestResult testResultRecent = setDefaultTestResult(testResultRepository, testCodeInfo, user, typeInfoRecent);

        // when
        Map<String, TypeInfoData> userTypeInfo = myPageService.getUserTypeInfo(user);

        // then
        assertThat(userTypeInfo).hasSize(1);
        assertThat(userTypeInfo.get("EXAMTEST")).isNotNull()
                .extracting("testCode", "testName")
                .containsExactly("EXAMTEST", "EXAM예제");
        assertThat(userTypeInfo.get("EXAMTEST")).extracting("typeInfo")
                .extracting("typeCode", "typeName")
                .containsExactly("타입코드2", "타임명2");
    }

    @Test
    @DisplayName("사용자 성향 정보를 조회하면 전체 응답 결과의 성향 수치가 계산되어 리턴된다")
    void getUserTendencyInfoTest() throws Exception {
        // given
        User user = setDefaultUser(userRepository);
        TestCodeInfo examTestCodeInfo = setDefaultExamTestCodeInfo(testCodeInfoRepository);
        List<TypeIndicator> typeIndicatorList = setDefaultTypeIndicator(typeIndicatorRepository, examTestCodeInfo);
        List<PersonalityAnswer> personalityAnswers1 = setDefaultAnswerList(personalityAnswerRepository, examTestCodeInfo, typeIndicatorList.get(0));
        TypeInfo typeInfo = setDefaultTypeInfo(typeInfoRepository, examTestCodeInfo);
        TestResult testResult = setDefaultTestResult(testResultRepository, examTestCodeInfo, user, typeInfo);
        setTestResultDetail(testResultDetailRepository, testResult, user, examTestCodeInfo, personalityAnswers1.get(0), 1);
        setTestResultDetail(testResultDetailRepository, testResult, user, examTestCodeInfo, personalityAnswers1.get(1), 2);

        // when
        UserTendencyInfo userTendencyInfo = myPageService.getUserTendencyInfo(user);

        // then
        assertThat(userTendencyInfo.getTendencyMap()).hasSize(6);
        assertThat(userTendencyInfo.getTendencyMap().get(Tendency.A)).isEqualTo(5L);
        assertThat(userTendencyInfo.getTendencyMap().get(Tendency.C)).isEqualTo(5L);

    }

    @Test
    @DisplayName("사용자 닉네임 변경시 지정한 닉네임으로 정상적으로 변경된다.")
    void updateNicknameTest() throws Exception {
        // given
        User user = setDefaultUser(userRepository);

        // when
        User updatedUser = myPageService.updateNickname(user.getId(), "updatedNickname");

        // then
        assertThat(updatedUser.getNickname()).isEqualTo("updatedNickname");
    }

    @Test
    @DisplayName("사용자 닉네임 변경시 존재하지 않는 ID면 Exception이 발생")
    void updatedNicknameFailTest() throws Exception {
        // given
        User user = setDefaultUser(userRepository);

        // when // then
        Assertions.assertThrows(TypetestException.class, () ->
                myPageService.updateNickname(-1L, "updatedNickname"));
    }

}