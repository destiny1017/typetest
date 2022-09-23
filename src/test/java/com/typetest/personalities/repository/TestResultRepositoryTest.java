package com.typetest.personalities.repository;

import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TestResultRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestResultDetailRepository testResultDetailRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private TestCodeInfoRepository testCodeInfoRepository;

    @BeforeEach
    public void beforeEach() {
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
        TypeInfo typeInfo4 = new TypeInfo(testCodeInfo2, "INTP", "인팁");
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(typeInfo4);

        em.flush();
    }

    @Test
    @DisplayName("사용자, 테스트결과, 테스트 응답 상세 데이터 저장 테스트")
    public void EntityInsertTest() throws Exception {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo = new TypeInfo(testCodeInfo1, "TEST", "테스트");
        TestResult pt = new TestResult(user, testCodeInfo1, typeInfo);

        TypeIndicator indicatorA = new TypeIndicator(testCodeInfo1, 1, "A지표");
        TypeIndicator indicatorB = new TypeIndicator(testCodeInfo1, 2, "B지표");
        TypeIndicator indicatorC = new TypeIndicator(testCodeInfo1, 3, "C지표");

        PersonalityQuestion testQuestion1 = new PersonalityQuestion(testCodeInfo1, "TestQuestion1", 1);
        PersonalityQuestion testQuestion2 = new PersonalityQuestion(testCodeInfo1, "TestQuestion2", 2);
        PersonalityQuestion testQuestion3 = new PersonalityQuestion(testCodeInfo1, "TestQuestion3", 3);

        PersonalityAnswer answer1 = PersonalityAnswer.builder()
                .personalityQuestion(testQuestion1)
                .testCode(testCodeInfo1)
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(indicatorA)
                .build();

        PersonalityAnswer answer2 = PersonalityAnswer.builder()
                .personalityQuestion(testQuestion1)
                .testCode(testCodeInfo1)
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(indicatorA)
                .build();

        PersonalityAnswer answer3 = PersonalityAnswer.builder()
                .personalityQuestion(testQuestion1)
                .testCode(testCodeInfo1)
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(indicatorA)
                .build();

        testQuestion1.addAnswer(answer1);
        testQuestion1.addAnswer(answer2);
        testQuestion1.addAnswer(answer3);

        TestResultDetail ptd1 = new TestResultDetail(pt, user, testCodeInfo1, 1, answer1);
        TestResultDetail ptd2 = new TestResultDetail(pt, user, testCodeInfo1, 2, answer2);
        TestResultDetail ptd3 = new TestResultDetail(pt, user, testCodeInfo1, 3, answer3);

        em.persist(user);
        em.persist(pt);
        em.persist(typeInfo);
        em.persist(ptd1);
        em.persist(ptd2);
        em.persist(ptd3);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(testQuestion1);
        em.persist(testQuestion2);
        em.persist(testQuestion3);

        em.flush();

        //when
        User findUser = userRepository.findById(user.getId()).get();
        TestResult findPt = testResultRepository.findById(pt.getId()).get();
        List<TestResultDetail> byTestResult = testResultDetailRepository.findByTestResult(pt);

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(byTestResult).contains(ptd1, ptd2, ptd3);

    }

    @Test
    @DisplayName("사용자 유형테스트 결과 여러개 저장 테스트")
    public void savePersonal() throws Exception {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo = new TypeInfo(testCodeInfo1, "TEST", "테스트");
        TestResult pt = new TestResult(user, testCodeInfo1, typeInfo);

        TypeIndicator indicatorA = new TypeIndicator(testCodeInfo1, 1, "A지표");
        TypeIndicator indicatorB = new TypeIndicator(testCodeInfo1, 2, "B지표");
        TypeIndicator indicatorC = new TypeIndicator(testCodeInfo1, 3, "C지표");

        PersonalityQuestion testQuestion1 = new PersonalityQuestion(testCodeInfo1, "TestQuestion1", 1);
        PersonalityQuestion testQuestion2 = new PersonalityQuestion(testCodeInfo1, "TestQuestion2", 2);
        PersonalityQuestion testQuestion3 = new PersonalityQuestion(testCodeInfo1, "TestQuestion3", 3);

        PersonalityAnswer answer1 = PersonalityAnswer.builder()
                .personalityQuestion(testQuestion1)
                .testCode(testCodeInfo1)
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(indicatorA)
                .build();

        PersonalityAnswer answer2 = PersonalityAnswer.builder()
                .personalityQuestion(testQuestion1)
                .testCode(testCodeInfo1)
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(indicatorA)
                .build();

        PersonalityAnswer answer3 = PersonalityAnswer.builder()
                .personalityQuestion(testQuestion1)
                .testCode(testCodeInfo1)
                .point(1)
                .tendency(Tendency.A)
                .typeIndicator(indicatorA)
                .build();

        TestResultDetail ptd1 = new TestResultDetail(pt, user, testCodeInfo1, 1, answer1);
        TestResultDetail ptd2 = new TestResultDetail(pt, user, testCodeInfo1, 2, answer2);
        TestResultDetail ptd3 = new TestResultDetail(pt, user, testCodeInfo1, 3, answer3);

        //when
        em.persist(typeInfo);
        userRepository.save(user);
        testCodeInfoRepository.save(testCodeInfo1);
        testResultRepository.save(pt);
        testResultDetailRepository.save(ptd1);
        testResultDetailRepository.save(ptd2);
        testResultDetailRepository.save(ptd3);

        em.persist(answer1);
        em.persist(answer2);
        em.persist(answer3);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(testQuestion1);
        em.persist(testQuestion2);
        em.persist(testQuestion3);

        User findUser = userRepository.findById(user.getId()).get();
        TestResult findPt = testResultRepository.findById(pt.getId()).get();
        List<TestResultDetail> byTestResult = testResultDetailRepository.findByTestResult(pt);

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(byTestResult).contains(ptd1, ptd2, ptd3);
    }
    
    @Test
    @DisplayName("사용자 유형정보 가져오기 테스트")
    void getUserTypeListTest() {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo2, "INTP", "인팁");
        TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo1);
        TestResult pt2 = new TestResult(user, testCodeInfo1, typeInfo2);
        TestResult pt3 = new TestResult(user, testCodeInfo2, typeInfo3);
        em.persist(user);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(pt1);
        em.persist(pt2);
        em.persist(pt3);

        em.flush();

        //when
        User testUser = User.builder().id(user.getId()).build();
        List<TypeInfoData> userTypeList = testResultRepository.getUserTypeList(testUser);

        //then
        assertThat(userTypeList).hasSize(3);

        assertThat(userTypeList.stream().map(u -> u.getTestCode())).contains("EXAMTEST");
        assertThat(userTypeList.stream().map(u -> u.getTestCode())).contains("CARDTEST");

        assertThat(userTypeList.stream().map(u -> u.getTypeInfo())).contains(typeInfo1);
        assertThat(userTypeList.stream().map(u -> u.getTypeInfo())).contains(typeInfo2);
        assertThat(userTypeList.stream().map(u -> u.getTypeInfo())).contains(typeInfo3);

    }

}