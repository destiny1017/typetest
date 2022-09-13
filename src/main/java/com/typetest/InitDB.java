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
                // EXAMTEST 테스트용 데이터
                TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM,
                        "https://d2k6w3n3qf94c4.cloudfront.net/media/banners/images/07_mmangsi.png",
                        "These Sass loops aren’t limited to color maps, either. You can also generate responsive variations of your components.",
                        1);
                testCodeInfo1.setPlayCount(145594);
                TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
                TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
                TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
                TypeInfo typeInfo5 = new TypeInfo(testCodeInfo1, "BBB", "비비비이");
                TypeInfo typeInfo6 = new TypeInfo(testCodeInfo1, "BBC", "비비씨?");
                typeInfo1.plusResultCount(10000);
                typeInfo2.plusResultCount(18000);
                typeInfo3.plusResultCount(25000);
                typeInfo5.plusResultCount(9000);
                typeInfo6.plusResultCount(14000);

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

                em.persist(testCodeInfo1);
                em.persist(typeInfo1);
                em.persist(typeInfo2);
                em.persist(typeInfo3);
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


                // CARDTEST 테스트용 데이터
                TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "분위기 컬러 테스트", AnswerType.CARD,
                        "https://cdn.banggooso.com/assets/images/game108/1651571103(2).gif",
                        "나의 분위기 컬러를 알아봐요!",
                        1);
                testCodeInfo2.setPlayCount(92843);

                TypeInfo typeInfoCard1 = new TypeInfo(testCodeInfo2, "BBA", "비비에이");
                TypeInfo typeInfoCard2 = new TypeInfo(testCodeInfo2, "AAA", "에에에이");
                TypeInfo typeInfoCard3 = new TypeInfo(testCodeInfo2, "BAB", "비에이비");
                TypeInfo typeInfoCard5 = new TypeInfo(testCodeInfo2, "BBB", "비비비이");
                TypeInfo typeInfoCard6 = new TypeInfo(testCodeInfo2, "BBC", "비비씨?");

                typeInfoCard1.plusResultCount(10000);
                typeInfoCard2.plusResultCount(8000);
                typeInfoCard3.plusResultCount(5000);
                typeInfoCard5.plusResultCount(9000);
                typeInfoCard6.plusResultCount(14000);

                TypeIndicator indicatorCardA = new TypeIndicator(testCodeInfo2, 1, "A지표");
                TypeIndicator indicatorCardB = new TypeIndicator(testCodeInfo2, 2, "B지표");
                TypeIndicator indicatorCardC = new TypeIndicator(testCodeInfo2, 3, "C지표");
                TypeIndicator indicatorListCard[] = {indicatorCardA, indicatorCardB, indicatorCardC};

                IndicatorSetting indicatorSettingCard1 = new IndicatorSetting(indicatorCardA, testCodeInfo2, 0, "B");
                IndicatorSetting indicatorSettingCard2 = new IndicatorSetting(indicatorCardA, testCodeInfo2, 6, "A");
                IndicatorSetting indicatorSettingCard3 = new IndicatorSetting(indicatorCardB, testCodeInfo2, 0, "B");
                IndicatorSetting indicatorSettingCard4 = new IndicatorSetting(indicatorCardB, testCodeInfo2, 6, "A");
                IndicatorSetting indicatorSettingCard5 = new IndicatorSetting(indicatorCardC, testCodeInfo2, 0, "B");
                IndicatorSetting indicatorSettingCard6 = new IndicatorSetting(indicatorCardC, testCodeInfo2, 6, "A");

                TypeDescription bbbDescriptionCard1 = new TypeDescription(typeInfoCard5, 1, "BBB description1");
                TypeDescription aaaDescriptionCard1 = new TypeDescription(typeInfoCard2, 1, "AAA description1");

                TypeImage bbbImageCard1 = new TypeImage(typeInfoCard5, 1, "https://cdn.banggooso.com/assets/images/game108/result/ENTP.jpg");
                TypeImage aaaImageCard1 = new TypeImage(typeInfoCard2, 1, "https://cdn.banggooso.com/assets/images/game108/result/ESTP.jpg");

                em.persist(testCodeInfo2);
                em.persist(typeInfoCard1);
                em.persist(typeInfoCard2);
                em.persist(typeInfoCard3);
                em.persist(typeInfoCard5);
                em.persist(typeInfoCard6);
                em.persist(indicatorCardA);
                em.persist(indicatorCardB);
                em.persist(indicatorCardC);
                em.persist(indicatorSettingCard1);
                em.persist(indicatorSettingCard2);
                em.persist(indicatorSettingCard3);
                em.persist(indicatorSettingCard4);
                em.persist(indicatorSettingCard5);
                em.persist(indicatorSettingCard6);
                em.persist(bbbDescriptionCard1);
                em.persist(aaaDescriptionCard1);
                em.persist(bbbImageCard1);
                em.persist(aaaImageCard1);


                User user = User.builder()
                            .name("김대호")
                            .email("eogh6428@gmail.com")
                            .picture("https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c")
                            .role(Role.ADMIN)
                            .nickname("디앙")
                            .build();
                TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo2);
                TestResult pt2 = new TestResult(user, testCodeInfo1, typeInfo1);
                em.persist(user);
                em.persist(pt1);
                em.persist(pt2);

                em.flush();


                List<PersonalityQuestion> questionList = new ArrayList<>();
                List<PersonalityQuestion> questionListCard = new ArrayList<>();

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

                personalityQuestionRepository.saveAll(questionList);


                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion1", 1, "https://cdn.banggooso.com/assets/images/game108/page/Q01.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion2", 2, "https://cdn.banggooso.com/assets/images/game108/page/Q02.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion3", 3, "https://cdn.banggooso.com/assets/images/game108/page/Q03.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion4", 4, "https://cdn.banggooso.com/assets/images/game108/page/Q04.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion5", 5, "https://cdn.banggooso.com/assets/images/game108/page/Q05.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion6", 6, "https://cdn.banggooso.com/assets/images/game108/page/Q06.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion7", 7, "https://cdn.banggooso.com/assets/images/game108/page/Q07.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion8", 8, "https://cdn.banggooso.com/assets/images/game108/page/Q08.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion9", 9, "https://cdn.banggooso.com/assets/images/game108/page/Q09.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion10", 10, "https://cdn.banggooso.com/assets/images/game108/page/Q10.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion11", 11, "https://cdn.banggooso.com/assets/images/game108/page/Q11.jpg"));
                questionListCard.add(new PersonalityQuestion(testCodeInfo2, "examQuestion12", 12, "https://cdn.banggooso.com/assets/images/game108/page/Q12.jpg"));

                int cnt2 = 0;
                for (int i = 0; i < questionListCard.size(); i++) {
                    for (int j = 1; j <= 3; j++) {
                        questionListCard.get(i).addAnswer(PersonalityAnswer.builder()
                                .personalityQuestion(questionListCard.get(i))
                                .testCode(testCodeInfo2)
                                .answer("example answer" + j)
                                .point(j)
                                .tendency(Tendency.A)
                                .typeIndicator(indicatorListCard[cnt2])
                                .build());
                    }
                    if((i+1) % (questionListCard.size() / 3) == 0 && cnt2 < indicatorList.length-1) cnt2++;
                }

                personalityQuestionRepository.saveAll(questionListCard);
            }

        }
    }
}
