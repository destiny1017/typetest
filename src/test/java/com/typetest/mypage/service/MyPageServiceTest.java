package com.typetest.mypage.service;

import com.typetest.IntegrationTestSupport;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.data.UserTendencyInfo;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.TestResultRepository;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.mypage.data.TypeInfoData;
import com.typetest.personalities.data.AnswerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Transactional
class MyPageServiceTest extends IntegrationTestSupport {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("사용자의 저장된 테스트 결과 정보 가져오기")
    void getUserTypeInfoTest() throws InterruptedException {
        // 임시 테스트코드
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        //given
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "AAB", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo2, "ISFP", "잇픕");
        Thread.sleep(1000);
        TypeInfo typeInfo4 = new TypeInfo(testCodeInfo1, "BBB", "비비비");
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);

        User user = User.builder()
                .name("김대호")
                .email("eogh6428@gmail.com")
                .picture("https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c")
                .role(Role.USER)
                .nickname("디앙")
                .build();

        em.persist(user);

        TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo2);
        TestResult pt2 = new TestResult(user, testCodeInfo2, typeInfo3);
        em.persist(pt1);
        em.persist(pt2);

        em.flush();

        //when
        Map<String, TypeInfoData> typeMap = myPageService.getUserTypeInfo(user);

        //then
        assertThat(typeMap).hasSize(2);
        assertThat(typeMap.get(testCodeInfo1.getTestCode()).getTypeInfo().getTestCode()).isEqualTo(typeInfo4.getTestCode());
        assertThat(typeMap.get(testCodeInfo2.getTestCode()).getTypeInfo().getTestCode()).isEqualTo(typeInfo3.getTestCode());
    }

    @Test @DisplayName("")
    void getUserTendencyInfoTest() {
        //given
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

        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
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
                .tendency(Tendency.C)
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
        UserTendencyInfo userTendencyInfo = myPageService.getUserTendencyInfo(user);

        //then
        assertThat(userTendencyInfo).isNotNull();
        assertThat(userTendencyInfo.getTendencyMap()).hasSize(6);
        assertThat(userTendencyInfo.getTendencyMap().get(Tendency.A)).isEqualTo(6);
        assertThat(userTendencyInfo.getTendencyMap().get(Tendency.C)).isEqualTo(3);
        assertThat(userTendencyInfo.getTendencyMap().get(Tendency.H)).isEqualTo(0);

    }
}