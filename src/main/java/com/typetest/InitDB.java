package com.typetest;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;
        private final Environment env;
        private final PersonalityQuestionRepository personalityQuestionRepository;

        public void init() {
            if(!env.getProperty("spring.profiles.active").equals("test")) {
                // 임시 테스트코드
//                TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
                TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM,
                        "https://image.utoimage.com/preview/cp880338/2018/11/201811006148_500.jpg",
                        "These Sass loops aren’t limited to color maps, either. You can also generate responsive variations of your components.");
                TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD,
                        "https://image.utoimage.com/preview/cp880338/2018/11/201811006148_500.jpg",
                        "These Sass loops aren’t limited to color maps, either. You can also generate responsive variations of your components.");
                TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
                TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
                TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
                TypeInfo typeInfo4 = new TypeInfo(testCodeInfo2, "INTP", "인팁");
                TypeInfo typeInfo5 = new TypeInfo(testCodeInfo1, "BBB", "비비비이");

                TypeIndicator indicatorA = new TypeIndicator(testCodeInfo1, 1, "A지표");
                TypeIndicator indicatorB = new TypeIndicator(testCodeInfo1, 2, "B지표");
                TypeIndicator indicatorC = new TypeIndicator(testCodeInfo1, 3, "C지표");
                TypeIndicator indicatorList[] = {indicatorA, indicatorB, indicatorC};

                IndicatorSetting indicatorSetting1 = new IndicatorSetting(indicatorA, 0, "B");
                IndicatorSetting indicatorSetting2 = new IndicatorSetting(indicatorA, 12, "A");
                IndicatorSetting indicatorSetting3 = new IndicatorSetting(indicatorB, 0, "B");
                IndicatorSetting indicatorSetting4 = new IndicatorSetting(indicatorB, 12, "A");
                IndicatorSetting indicatorSetting5 = new IndicatorSetting(indicatorC, 0, "B");
                IndicatorSetting indicatorSetting6 = new IndicatorSetting(indicatorC, 12, "A");

                TypeDescription bbbDescription1 = new TypeDescription(typeInfo5, 1, "BBB description1");
                TypeDescription aaaDescription1 = new TypeDescription(typeInfo2, 1, "AAA description1");

                TypeImage bbbImage1 = new TypeImage(typeInfo5, 1, "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200");
                TypeImage aaaImage1 = new TypeImage(typeInfo2, 1, "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200");

                em.persist(testCodeInfo1);
                em.persist(testCodeInfo2);
                em.persist(typeInfo1);
                em.persist(typeInfo2);
                em.persist(typeInfo3);
                em.persist(typeInfo4);
                em.persist(typeInfo5);
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

                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion1", 1));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion2", 2));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion3", 3));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion4", 4));

                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion5", 5));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion6", 6));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion7", 7));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion8", 8));

                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion9", 9));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion10", 10));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion11", 11));
                questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion12", 12));

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
                    if((i+1) % 4 == 0) cnt++;
                }

                personalityQuestionRepository.saveAll(questionList);
            }

        }
    }
}
