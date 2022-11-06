package com.typetest.mypage.controller;

import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.dto.SessionUser;
import com.typetest.user.repository.UserRepository;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MyPageControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;
    @Autowired
    PersonalityQuestionRepository personalityQuestionRepository;

    @BeforeEach
    void beforeEach() {
        // 임시 테스트코드
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM,
                "https://image.utoimage.com/preview/cp880338/2018/11/201811006148_500.jpg",
                "These Sass loops aren’t limited to color maps, either. You can also generate responsive variations of your components.",
                0);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD,
                "https://image.utoimage.com/preview/cp880338/2018/11/201811006148_500.jpg",
                "These Sass loops aren’t limited to color maps, either. You can also generate responsive variations of your components.",
                0);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
        TypeInfo typeInfo4 = new TypeInfo(testCodeInfo2, "INTP", "인팁");
        TypeInfo typeInfo5 = new TypeInfo(testCodeInfo1, "BBB", "비비비이");
        TypeInfo typeInfo6 = new TypeInfo(testCodeInfo1, "BBC", "비비씨?");

        TypeIndicator indicatorA = new TypeIndicator(testCodeInfo1, 1, "A지표");
        TypeIndicator indicatorB = new TypeIndicator(testCodeInfo1, 2, "B지표");
        TypeIndicator indicatorC = new TypeIndicator(testCodeInfo1, 3, "C지표");
        TypeIndicator indicatorList[] = {indicatorA, indicatorB, indicatorC};

        IndicatorSetting indicatorSetting1 = new IndicatorSetting(indicatorA, testCodeInfo1, 0, "B");
        IndicatorSetting indicatorSetting2 = new IndicatorSetting(indicatorA, testCodeInfo1, 12, "A");
        IndicatorSetting indicatorSetting3 = new IndicatorSetting(indicatorB, testCodeInfo1, 0, "B");
        IndicatorSetting indicatorSetting4 = new IndicatorSetting(indicatorB, testCodeInfo1, 12, "A");
        IndicatorSetting indicatorSetting5 = new IndicatorSetting(indicatorC, testCodeInfo1, 0, "B");
        IndicatorSetting indicatorSetting6 = new IndicatorSetting(indicatorC, testCodeInfo1, 12, "A");

        TypeDescription bbbDescription1 = new TypeDescription(typeInfo5, 1, "BBB description1");
        TypeDescription aaaDescription1 = new TypeDescription(typeInfo2, 1, "AAA description1");

        TypeImage bbbImage1 = new TypeImage(typeInfo5, 1, "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200");
        TypeImage aaaImage1 = new TypeImage(typeInfo2, 1, "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200");

        TypeRelation typeRelation1 = new TypeRelation(typeInfo2, typeInfo1, typeInfo5);
        TypeRelation typeRelation2 = new TypeRelation(typeInfo5, typeInfo3, typeInfo2);
//
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(typeInfo4);
        em.persist(typeInfo5);
        em.persist(typeInfo6);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(indicatorSetting1);
        em.persist(indicatorSetting2);
        em.persist(indicatorSetting3);
        em.persist(indicatorSetting4);
        em.persist(indicatorSetting5);
        em.persist(indicatorSetting6);
        em.persist(bbbDescription1);
        em.persist(aaaDescription1);
        em.persist(bbbImage1);
        em.persist(aaaImage1);
        em.persist(typeRelation1);
        em.persist(typeRelation2);

        User user = User.builder()
                .name("김대호")
                .email("eogh6428@gmail.com")
                .picture("https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo2);
        TestResult pt2 = new TestResult(user, testCodeInfo1, typeInfo1);
        TestResult pt3 = new TestResult(user, testCodeInfo2, typeInfo4);
        em.persist(user);
        em.persist(pt1);
        em.persist(pt2);
        em.persist(pt3);

        em.flush();


        List<PersonalityQuestion> questionList = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion"+i, i));
        }

        int cnt = 0;
        for (int i = 0; i < questionList.size(); i++) {
            for (int j = 1; j <= 5; j++) {
                questionList.get(i).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionList.get(i))
                        .testCode(testCodeInfo1)
                        .point(j)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorList[cnt])
                        .build());
            }
            if((i+1) % (questionList.size() / 3) == 0 && cnt < indicatorList.length-1) cnt++;
        }
        TestResult pt = new TestResult(user, testCodeInfo1, typeInfo1);
        TestResultDetail ptd1 = new TestResultDetail(pt, user, testCodeInfo1, 1, questionList.get(0).getAnswerList().get(0));
        TestResultDetail ptd2 = new TestResultDetail(pt, user, testCodeInfo1, 2, questionList.get(1).getAnswerList().get(0));
        TestResultDetail ptd3 = new TestResultDetail(pt, user, testCodeInfo1, 3, questionList.get(2).getAnswerList().get(0));
        em.persist(pt);
        em.persist(ptd1);
        em.persist(ptd2);
        em.persist(ptd3);

        personalityQuestionRepository.saveAll(questionList);
    }

    @Test
    void myPage() throws Exception {
        // user정보 없음
        mockMvc.perform(MockMvcRequestBuilders.get("/myPage"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // user정보 있음
        User user = userRepository.findByEmail("eogh6428@gmail.com").get();
        SessionUser sessionUser = new SessionUser(user);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", sessionUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/myPage")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("userTypeMap"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userTendencyInfo"));
    }
}