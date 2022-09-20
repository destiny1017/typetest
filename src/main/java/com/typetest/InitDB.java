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
                // 테스트용 사용자 데이터
                User user = User.builder()
                        .name("김대호")
                        .email("eogh6428@gmail.com")
                        .picture("https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c")
                        .role(Role.ADMIN)
                        .nickname("디앙")
                        .build();
                em.persist(user);



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

                TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo2);
                TestResult pt2 = new TestResult(user, testCodeInfo1, typeInfo1);
                em.persist(pt1);
                em.persist(pt2);
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

                personalityQuestionRepository.saveAll(questionList);


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


                List<PersonalityQuestion> questionListCard = new ArrayList<>();

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


                // CUSTOMTEST 테스트용 데이터
                TestCodeInfo testCodeInfo3 = new TestCodeInfo("CUSTOMTEST", "행운의 새 테스트", AnswerType.CARD,
                        "https://cdn.banggooso.com/assets/images/game82/1638929196(2).gif",
                        "두근 두근 나의 행운의 새는?",
                        1);
                testCodeInfo3.setPlayCount(56258);

                TypeInfo typeInfoCustom1 = new TypeInfo(testCodeInfo3,  "INTP", "");
                TypeInfo typeInfoCustom2 = new TypeInfo(testCodeInfo3,  "ENTP", "");
                TypeInfo typeInfoCustom3 = new TypeInfo(testCodeInfo3,  "ISTP", "");
                TypeInfo typeInfoCustom4 = new TypeInfo(testCodeInfo3,  "ESTP", "");
                TypeInfo typeInfoCustom5 = new TypeInfo(testCodeInfo3,  "INFP", "");
                TypeInfo typeInfoCustom6 = new TypeInfo(testCodeInfo3,  "ENFP", "");
                TypeInfo typeInfoCustom7 = new TypeInfo(testCodeInfo3,  "INTJ", "");
                TypeInfo typeInfoCustom8 = new TypeInfo(testCodeInfo3,  "ENTJ", "");
                TypeInfo typeInfoCustom9 = new TypeInfo(testCodeInfo3,  "INTP", "");
                TypeInfo typeInfoCustom10 = new TypeInfo(testCodeInfo3, "ENFJ", "");
                TypeInfo typeInfoCustom11 = new TypeInfo(testCodeInfo3, "ESTJ", "");
                TypeInfo typeInfoCustom12 = new TypeInfo(testCodeInfo3, "ESFP", "");
                TypeInfo typeInfoCustom13 = new TypeInfo(testCodeInfo3, "ESFJ", "");
                TypeInfo typeInfoCustom14 = new TypeInfo(testCodeInfo3, "ISFP", "");
                TypeInfo typeInfoCustom15 = new TypeInfo(testCodeInfo3, "ISFJ", "");
                TypeInfo typeInfoCustom16 = new TypeInfo(testCodeInfo3, "ISTJ", "");

                TypeDescription descriptionCustom1_1  = new TypeDescription(typeInfoCustom1 , 1,
                        "나의 행운의 새는\n" +
                                "내 맘대로 다 되는\n" +
                                "투명한 큰부리큰기러기");
                TypeDescription descriptionCustom1_2  = new TypeDescription(typeInfoCustom1 , 2,
                        "큰부리큰기러기를 닮은 나는\n" +
                                "인생을 항상 계산적으로 살지만 내 사람들에겐 계산하지 않고 잘해줘요.\n" +
                                "총대 메고 싫은 말 하는 것을 좋아하진 않지만 이미 내가 나서고 있어요.\n" +
                                "주변에서 표정관리를 못한다고 하는데 사실 안 하는 거예요.\n" +
                                "가끔 내가 존멋인 것 같다고 느껴요.\n" +
                                "내가 제일 우울할 때는 무능하다고 느낄 때예요.");
                TypeDescription descriptionCustom1_3  = new TypeDescription(typeInfoCustom1 , 3,
                        "큰부리큰기러기가 줄 올겨울 행운은?\n" +
                                "모두가 나의 똑똑함을 알아주는 행운\n" +
                                "쓸모없는 곳 포함 언제 어디서나 1등 하는 행운\n" +
                                "내가 하자는 대로 다 해주는 친구들을 만난 행운\n" +
                                "올 초에 세운 연말 계획을 거의 이룬 행운");
                TypeDescription descriptionCustom1_4  = new TypeDescription(typeInfoCustom1 , 4,
                        "“인생조차 효율 갑”");

                TypeDescription descriptionCustom2_1  = new TypeDescription(typeInfoCustom2 , 1,
                        "");
                TypeDescription descriptionCustom2_2  = new TypeDescription(typeInfoCustom2 , 2,
                        "");
                TypeDescription descriptionCustom2_3  = new TypeDescription(typeInfoCustom2 , 3,
                        "");
                TypeDescription descriptionCustom2_4  = new TypeDescription(typeInfoCustom2 , 4,
                        "");

                TypeDescription descriptionCustom3_1  = new TypeDescription(typeInfoCustom3 , 1,
                        "");
                TypeDescription descriptionCustom3_2  = new TypeDescription(typeInfoCustom3 , 2,
                        "");
                TypeDescription descriptionCustom3_3  = new TypeDescription(typeInfoCustom3 , 3,
                        "");
                TypeDescription descriptionCustom3_4  = new TypeDescription(typeInfoCustom3 , 4,
                        "");

                TypeDescription descriptionCustom4_1  = new TypeDescription(typeInfoCustom4 , 1,
                        "");
                TypeDescription descriptionCustom4_2  = new TypeDescription(typeInfoCustom4 , 2,
                        "");
                TypeDescription descriptionCustom4_3  = new TypeDescription(typeInfoCustom4 , 3,
                        "");
                TypeDescription descriptionCustom4_4  = new TypeDescription(typeInfoCustom4 , 4,
                        "");

                TypeDescription descriptionCustom5_1  = new TypeDescription(typeInfoCustom5 , 1,
                        "나의 행운의 새는\n" +
                                "내 맘대로 다 되는\n" +
                                "투명한 큰부리큰기러기");
                TypeDescription descriptionCustom5_2  = new TypeDescription(typeInfoCustom5 , 2,
                        "큰부리큰기러기를 닮은 나는\n" +
                                "인생을 항상 계산적으로 살지만 내 사람들에겐 계산하지 않고 잘해줘요.\n" +
                                "총대 메고 싫은 말 하는 것을 좋아하진 않지만 이미 내가 나서고 있어요.\n" +
                                "주변에서 표정관리를 못한다고 하는데 사실 안 하는 거예요.\n" +
                                "가끔 내가 존멋인 것 같다고 느껴요.\n" +
                                "내가 제일 우울할 때는 무능하다고 느낄 때예요.");
                TypeDescription descriptionCustom5_3  = new TypeDescription(typeInfoCustom5 , 3,
                        "큰부리큰기러기가 줄 올겨울 행운은?\n" +
                                "모두가 나의 똑똑함을 알아주는 행운\n" +
                                "쓸모없는 곳 포함 언제 어디서나 1등 하는 행운\n" +
                                "내가 하자는 대로 다 해주는 친구들을 만난 행운\n" +
                                "올 초에 세운 연말 계획을 거의 이룬 행운");
                TypeDescription descriptionCustom5_4  = new TypeDescription(typeInfoCustom5 , 4,
                        "“인생조차 효율 갑”");

                TypeDescription descriptionCustom6_1  = new TypeDescription(typeInfoCustom6 , 1,
                        "");
                TypeDescription descriptionCustom6_2  = new TypeDescription(typeInfoCustom6 , 2,
                        "");
                TypeDescription descriptionCustom6_3  = new TypeDescription(typeInfoCustom6 , 3,
                        "");
                TypeDescription descriptionCustom6_4  = new TypeDescription(typeInfoCustom6 , 4,
                        "");

                TypeDescription descriptionCustom7_1  = new TypeDescription(typeInfoCustom7 , 1,
                        "");
                TypeDescription descriptionCustom7_2  = new TypeDescription(typeInfoCustom7 , 2,
                        "");
                TypeDescription descriptionCustom7_3  = new TypeDescription(typeInfoCustom7 , 3,
                        "");
                TypeDescription descriptionCustom7_4  = new TypeDescription(typeInfoCustom7 , 4,
                        "");

                TypeDescription descriptionCustom8_1  = new TypeDescription(typeInfoCustom8 , 1,
                        "");
                TypeDescription descriptionCustom8_2  = new TypeDescription(typeInfoCustom8 , 2,
                        "");
                TypeDescription descriptionCustom8_3  = new TypeDescription(typeInfoCustom8 , 3,
                        "");
                TypeDescription descriptionCustom8_4  = new TypeDescription(typeInfoCustom8 , 4,
                        "");

                TypeDescription descriptionCustom9_1  = new TypeDescription(typeInfoCustom9 , 1,
                        "");
                TypeDescription descriptionCustom9_2  = new TypeDescription(typeInfoCustom9 , 2,
                        "");
                TypeDescription descriptionCustom9_3  = new TypeDescription(typeInfoCustom9 , 3,
                        "");
                TypeDescription descriptionCustom9_4  = new TypeDescription(typeInfoCustom9 , 4,
                        "");

                TypeDescription descriptionCustom10_1  = new TypeDescription(typeInfoCustom10 , 1,
                        "");
                TypeDescription descriptionCustom10_2  = new TypeDescription(typeInfoCustom10 , 2,
                        "");
                TypeDescription descriptionCustom10_3  = new TypeDescription(typeInfoCustom10 , 3,
                        "");
                TypeDescription descriptionCustom10_4  = new TypeDescription(typeInfoCustom10 , 4,
                        "");

                TypeDescription descriptionCustom11_1  = new TypeDescription(typeInfoCustom11 , 1,
                        "");
                TypeDescription descriptionCustom11_2  = new TypeDescription(typeInfoCustom11 , 2,
                        "");
                TypeDescription descriptionCustom11_3  = new TypeDescription(typeInfoCustom11 , 3,
                        "");
                TypeDescription descriptionCustom11_4  = new TypeDescription(typeInfoCustom11 , 4,
                        "");

                TypeDescription descriptionCustom12_1  = new TypeDescription(typeInfoCustom12 , 1,
                        "");
                TypeDescription descriptionCustom12_2  = new TypeDescription(typeInfoCustom12 , 2,
                        "");
                TypeDescription descriptionCustom12_3  = new TypeDescription(typeInfoCustom12 , 3,
                        "");
                TypeDescription descriptionCustom12_4  = new TypeDescription(typeInfoCustom12 , 4,
                        "");

                TypeDescription descriptionCustom13_1  = new TypeDescription(typeInfoCustom13 , 1,
                        "");
                TypeDescription descriptionCustom13_2  = new TypeDescription(typeInfoCustom13 , 2,
                        "");
                TypeDescription descriptionCustom13_3  = new TypeDescription(typeInfoCustom13 , 3,
                        "");
                TypeDescription descriptionCustom13_4  = new TypeDescription(typeInfoCustom13 , 4,
                        "");

                TypeDescription descriptionCustom14_1  = new TypeDescription(typeInfoCustom14 , 1,
                        "");
                TypeDescription descriptionCustom14_2  = new TypeDescription(typeInfoCustom14 , 2,
                        "");
                TypeDescription descriptionCustom14_3  = new TypeDescription(typeInfoCustom14 , 3,
                        "");
                TypeDescription descriptionCustom14_4  = new TypeDescription(typeInfoCustom14 , 4,
                        "");

                TypeDescription descriptionCustom15_1  = new TypeDescription(typeInfoCustom15 , 1,
                        "");
                TypeDescription descriptionCustom15_2  = new TypeDescription(typeInfoCustom15 , 2,
                        "");
                TypeDescription descriptionCustom15_3  = new TypeDescription(typeInfoCustom15 , 3,
                        "");
                TypeDescription descriptionCustom15_4  = new TypeDescription(typeInfoCustom15 , 4,
                        "");

                TypeDescription descriptionCustom16_1  = new TypeDescription(typeInfoCustom16 , 1,
                        "");
                TypeDescription descriptionCustom16_2  = new TypeDescription(typeInfoCustom16 , 2,
                        "");
                TypeDescription descriptionCustom16_3  = new TypeDescription(typeInfoCustom16 , 3,
                        "");
                TypeDescription descriptionCustom16_4  = new TypeDescription(typeInfoCustom16 , 4,
                        "");

                TypeImage imageCustom1  = new TypeImage(typeInfoCustom1 , 1, "https://cdn.banggooso.com/assets/images/game82/result/INTP.png");
                TypeImage imageCustom2  = new TypeImage(typeInfoCustom2 , 1, "https://cdn.banggooso.com/assets/images/game82/result/ENTP.png");
                TypeImage imageCustom3  = new TypeImage(typeInfoCustom3 , 1, "https://cdn.banggooso.com/assets/images/game82/result/ISTP.png");
                TypeImage imageCustom4  = new TypeImage(typeInfoCustom4 , 1, "https://cdn.banggooso.com/assets/images/game82/result/ESTP.png");
                TypeImage imageCustom5  = new TypeImage(typeInfoCustom5 , 1, "https://cdn.banggooso.com/assets/images/game82/result/INFP.png");
                TypeImage imageCustom6  = new TypeImage(typeInfoCustom6 , 1, "https://cdn.banggooso.com/assets/images/game82/result/ENFP.png");
                TypeImage imageCustom7  = new TypeImage(typeInfoCustom7 , 1, "https://cdn.banggooso.com/assets/images/game82/result/INTJ.png");
                TypeImage imageCustom8  = new TypeImage(typeInfoCustom8 , 1, "https://cdn.banggooso.com/assets/images/game82/result/ENTJ.png");
                TypeImage imageCustom9  = new TypeImage(typeInfoCustom9 , 1, "https://cdn.banggooso.com/assets/images/game82/result/INTP.png");
                TypeImage imageCustom10 = new TypeImage(typeInfoCustom10, 1, "https://cdn.banggooso.com/assets/images/game82/result/ENFJ.png");
                TypeImage imageCustom11 = new TypeImage(typeInfoCustom11, 1, "https://cdn.banggooso.com/assets/images/game82/result/ESTJ.png");
                TypeImage imageCustom12 = new TypeImage(typeInfoCustom12, 1, "https://cdn.banggooso.com/assets/images/game82/result/ESFP.png");
                TypeImage imageCustom13 = new TypeImage(typeInfoCustom13, 1, "https://cdn.banggooso.com/assets/images/game82/result/ESFJ.png");
                TypeImage imageCustom14 = new TypeImage(typeInfoCustom14, 1, "https://cdn.banggooso.com/assets/images/game82/result/ISFP.png");
                TypeImage imageCustom15 = new TypeImage(typeInfoCustom15, 1, "https://cdn.banggooso.com/assets/images/game82/result/ISFJ.png");
                TypeImage imageCustom16 = new TypeImage(typeInfoCustom16, 1, "https://cdn.banggooso.com/assets/images/game82/result/ISTJ.png");

                typeInfoCustom1 .plusResultCount(10000);
                typeInfoCustom2 .plusResultCount(8000);
                typeInfoCustom3 .plusResultCount(5000);
                typeInfoCustom4 .plusResultCount(9000);
                typeInfoCustom5 .plusResultCount(14000);
                typeInfoCustom6 .plusResultCount(10000);
                typeInfoCustom7 .plusResultCount(8000);
                typeInfoCustom8 .plusResultCount(5000);
                typeInfoCustom9 .plusResultCount(9000);
                typeInfoCustom10.plusResultCount(14000);
                typeInfoCustom11.plusResultCount(10000);
                typeInfoCustom12.plusResultCount(8000);
                typeInfoCustom13.plusResultCount(5000);
                typeInfoCustom14.plusResultCount(9000);
                typeInfoCustom15.plusResultCount(14000);
                typeInfoCustom16.plusResultCount(14000);

                TypeIndicator indicatorCustomA = new TypeIndicator(testCodeInfo3, 1, "IE지표");
                TypeIndicator indicatorCustomB = new TypeIndicator(testCodeInfo3, 2, "NS지표");
                TypeIndicator indicatorCustomC = new TypeIndicator(testCodeInfo3, 3, "FT지표");
                TypeIndicator indicatorCustomD = new TypeIndicator(testCodeInfo3, 4, "PJ지표");
                TypeIndicator indicatorListCustom[] = {indicatorCustomA, indicatorCustomB, indicatorCustomC, indicatorCustomD};

                IndicatorSetting indicatorSettingCustom1 = new IndicatorSetting(indicatorCustomA, testCodeInfo3, 0, "I");
                IndicatorSetting indicatorSettingCustom2 = new IndicatorSetting(indicatorCustomA, testCodeInfo3, 6, "E");
                IndicatorSetting indicatorSettingCustom3 = new IndicatorSetting(indicatorCustomB, testCodeInfo3, 0, "N");
                IndicatorSetting indicatorSettingCustom4 = new IndicatorSetting(indicatorCustomB, testCodeInfo3, 6, "S");
                IndicatorSetting indicatorSettingCustom5 = new IndicatorSetting(indicatorCustomC, testCodeInfo3, 0, "F");
                IndicatorSetting indicatorSettingCustom6 = new IndicatorSetting(indicatorCustomC, testCodeInfo3, 6, "T");
                IndicatorSetting indicatorSettingCustom7 = new IndicatorSetting(indicatorCustomD, testCodeInfo3, 0, "P");
                IndicatorSetting indicatorSettingCustom8 = new IndicatorSetting(indicatorCustomD, testCodeInfo3, 6, "J");

                em.persist(testCodeInfo3);
                em.persist(typeInfoCustom1 );
                em.persist(typeInfoCustom2 );
                em.persist(typeInfoCustom3 );
                em.persist(typeInfoCustom4 );
                em.persist(typeInfoCustom5 );
                em.persist(typeInfoCustom6 );
                em.persist(typeInfoCustom7 );
                em.persist(typeInfoCustom8 );
                em.persist(typeInfoCustom9 );
                em.persist(typeInfoCustom10);
                em.persist(typeInfoCustom11);
                em.persist(typeInfoCustom12);
                em.persist(typeInfoCustom13);
                em.persist(typeInfoCustom14);
                em.persist(typeInfoCustom15);
                em.persist(typeInfoCustom16);
                em.persist(indicatorCustomA);
                em.persist(indicatorCustomB);
                em.persist(indicatorCustomC);
                em.persist(indicatorCustomD);
                em.persist(indicatorSettingCustom1);
                em.persist(indicatorSettingCustom2);
                em.persist(indicatorSettingCustom3);
                em.persist(indicatorSettingCustom4);
                em.persist(indicatorSettingCustom5);
                em.persist(indicatorSettingCustom6);
                em.persist(indicatorSettingCustom7);
                em.persist(indicatorSettingCustom8);
                em.persist(descriptionCustom1_1);
                em.persist(descriptionCustom1_2);
                em.persist(descriptionCustom1_3);
                em.persist(descriptionCustom1_4);

                em.persist(descriptionCustom2_1);
                em.persist(descriptionCustom2_2);
                em.persist(descriptionCustom2_3);
                em.persist(descriptionCustom2_4);

                em.persist(descriptionCustom3_1);
                em.persist(descriptionCustom3_2);
                em.persist(descriptionCustom3_3);
                em.persist(descriptionCustom3_4);

                em.persist(descriptionCustom4_1);
                em.persist(descriptionCustom4_2);
                em.persist(descriptionCustom4_3);
                em.persist(descriptionCustom4_4);

                em.persist(descriptionCustom5_1);
                em.persist(descriptionCustom5_2);
                em.persist(descriptionCustom5_3);
                em.persist(descriptionCustom5_4);

                em.persist(descriptionCustom6_1);
                em.persist(descriptionCustom6_2);
                em.persist(descriptionCustom6_3);
                em.persist(descriptionCustom6_4);

                em.persist(descriptionCustom7_1);
                em.persist(descriptionCustom7_2);
                em.persist(descriptionCustom7_3);
                em.persist(descriptionCustom7_4);

                em.persist(descriptionCustom8_1);
                em.persist(descriptionCustom8_2);
                em.persist(descriptionCustom8_3);
                em.persist(descriptionCustom8_4);

                em.persist(descriptionCustom9_1);
                em.persist(descriptionCustom9_2);
                em.persist(descriptionCustom9_3);
                em.persist(descriptionCustom9_4);

                em.persist(descriptionCustom10_1);
                em.persist(descriptionCustom10_2);
                em.persist(descriptionCustom10_3);
                em.persist(descriptionCustom10_4);

                em.persist(descriptionCustom11_1);
                em.persist(descriptionCustom11_2);
                em.persist(descriptionCustom11_3);
                em.persist(descriptionCustom11_4);

                em.persist(descriptionCustom12_1);
                em.persist(descriptionCustom12_2);
                em.persist(descriptionCustom12_3);
                em.persist(descriptionCustom12_4);

                em.persist(descriptionCustom13_1);
                em.persist(descriptionCustom13_2);
                em.persist(descriptionCustom13_3);
                em.persist(descriptionCustom13_4);

                em.persist(descriptionCustom14_1);
                em.persist(descriptionCustom14_2);
                em.persist(descriptionCustom14_3);
                em.persist(descriptionCustom14_4);

                em.persist(descriptionCustom15_1);
                em.persist(descriptionCustom15_2);
                em.persist(descriptionCustom15_3);
                em.persist(descriptionCustom15_4);

                em.persist(descriptionCustom16_1);
                em.persist(descriptionCustom16_2);
                em.persist(descriptionCustom16_3);
                em.persist(descriptionCustom16_4);

                em.persist(imageCustom1 );
                em.persist(imageCustom2 );
                em.persist(imageCustom3 );
                em.persist(imageCustom4 );
                em.persist(imageCustom5 );
                em.persist(imageCustom6 );
                em.persist(imageCustom7 );
                em.persist(imageCustom8 );
                em.persist(imageCustom9 );
                em.persist(imageCustom10);
                em.persist(imageCustom11);
                em.persist(imageCustom12);
                em.persist(imageCustom13);
                em.persist(imageCustom14);
                em.persist(imageCustom15);
                em.persist(imageCustom16);


                List<PersonalityQuestion> questionListCustom = new ArrayList<>();

                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "도착한 나의 꿈 속!<br>두 갈래 길이 펼쳐졌는데, 내가 선택할 길은?", 1, "https://cdn.banggooso.com/assets/images/game82/page/Q01.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "두 갈래 길을 지나고 나니..<br>이제 어디로 가야하지?!<br>저 쪽의 새들의 무리가 보이는데!", 2, "https://cdn.banggooso.com/assets/images/game82/page/Q02.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "행운의 새를 만나는 곳까지 나를 태워다준다고?!<br>나와 함께 갈 새는", 3, "https://cdn.banggooso.com/assets/images/game82/page/Q03.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "‘유니온타워 꼭대기도 보이네!’<br>하늘을 날 때 느끼는 기분은?", 4, "https://cdn.banggooso.com/assets/images/game82/page/Q04.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "가다가 잠시 쉬기 위해 도착한 하남시 당정뜰!<br>도착한 나는..", 5, "https://cdn.banggooso.com/assets/images/game82/page/Q05.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "뜰에서 함께 사진을 찍자는 새들!<br>나는 어느 위치에?", 6, "https://cdn.banggooso.com/assets/images/game82/page/Q06.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "이제 출발하려는데 좀 만 더 쉬자는 새들!", 7, "https://cdn.banggooso.com/assets/images/game82/page/Q07.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "다시 가던 중, 이사 온 겨울 철새들 발견!<br>도움을 요청하는데 내가 할 일은?", 8, "https://cdn.banggooso.com/assets/images/game82/page/Q08.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "도와줘서 고맙다고 선물을 주는데..<br>내가 선택할 선물은?", 9, "https://cdn.banggooso.com/assets/images/game82/page/Q09.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "드디어 행운의 새를 만날 장소에 도착!<br>데려다 준 새가 이별 노래를 불러주는데..", 10, "https://cdn.banggooso.com/assets/images/game82/page/Q10.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "데려다 준 새에게<br>감사의 이별 인사를 건넨다면!", 11, "https://cdn.banggooso.com/assets/images/game82/page/Q11.png"));
                questionListCustom.add(new PersonalityQuestion(testCodeInfo3, "다시 만난 행운의 새!<br>꿈에서 깨면 한 가지 행운이 찾아올거라고..<br>내가 원하는 행운은?", 12, "https://cdn.banggooso.com/assets/images/game82/page/Q12.png"));


                questionListCustom.get(0).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("저 멀리까지 잘 보이는 쭉 뻗은 메타세콰이어길")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomA)
                        .build());

                questionListCustom.get(0).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("끝까지 안보여서 궁금증을 자극하는 길")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomA)
                        .build());



                questionListCustom.get(1).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("멀리서 부터 인사하며 물어 보러간다.")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomA)
                        .build());

                questionListCustom.get(1).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("쭈뼛쭈뼛 조심스럽게 다가가본다.")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomA)
                        .build());

                questionListCustom.get(2).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("안정감이 넘쳐 보이는 큰 새")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomA)
                        .build());

                questionListCustom.get(2).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("다정다감하게 이 곳을 소개해 줄 새")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomA)
                        .build());

                questionListCustom.get(3).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("내가 날고 있다니!! 너무 신기해~!!")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomB)
                        .build());

                questionListCustom.get(3).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("대박이야!! 내가 직접 날 수 있다면 어떨까?")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomB)
                        .build());

                questionListCustom.get(4).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("뜰을 전체적으로 살펴볼 수 있는 지도를 본다.")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomB)
                        .build());

                questionListCustom.get(4).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("가장 눈길을 끄는 곳에 가본다.")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomB)
                        .build());

                questionListCustom.get(5).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("사진은 오로지 센터 -★")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomB)
                        .build());

                questionListCustom.get(5).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("어디든 상관없는데.. 센터는 부담!")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomB)
                        .build());

                questionListCustom.get(6).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("‘그럼 30분만 더 쉬자!’<br>언제 다시 출발할지 정해놓고 쉰다.")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomC)
                        .build());

                questionListCustom.get(6).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("‘그래 알겠어~! 좀만 더 쉬자!’<br>더 놀면서 쉴 만큼 충분히 쉬고 간다.")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomC)
                        .build());

                questionListCustom.get(7).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("눈에 보이는 짐들을 다 풀어주기!")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomC)
                        .build());

                questionListCustom.get(7).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("다 풀어진 짐들을 종류별로 정리해주기!")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomC)
                        .build());

                questionListCustom.get(8).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("고가의 선물이 담긴 박스!")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomC)
                        .build());

                questionListCustom.get(8).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("최저가와 최고가 둘 중 하나가 담긴<br>복불복 랜덤 선물 박스!")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomC)
                        .build());

                questionListCustom.get(9).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("질 수 없지! 노래 배틀이다!<br>나도 노래를 불러준다.")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomD)
                        .build());

                questionListCustom.get(9).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("갑자기 노래를..?<br>뚝딱이 리액션을 하며 노래를 듣는다.")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomD)
                        .build());

                questionListCustom.get(10).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("데려다 줘서 고마워!<br>너의 비행 실력은 따라 올 새가 없다!!")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomD)
                        .build());

                questionListCustom.get(10).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("데려다 줘서 고마워!<br>오랫동안 비행하느라 너무 고생했어ㅠㅠ!")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomD)
                        .build());

                questionListCustom.get(11).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("올 겨울, 사람들과 함께 보내는 따뜻한 연말")
                        .point(1)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomD)
                        .build());

                questionListCustom.get(11).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionListCustom.get(0))
                        .testCode(testCodeInfo3)
                        .answer("올 겨울, 소망했던 것을 이루는 연말")
                        .point(4)
                        .tendency(Tendency.A)
                        .typeIndicator(indicatorCustomD)
                        .build());



//                int cnt3 = 0;
//                for (int i = 0; i < questionListCustom.size(); i++) {
//                    for (int j = 1; j <= 3; j++) {
//                        questionListCustom.get(i).addAnswer(PersonalityAnswer.builder()
//                                .personalityQuestion(questionListCustom.get(i))
//                                .testCode(testCodeInfo3)
//                                .answer("example answer" + j)
//                                .point(j)
//                                .tendency(Tendency.A)
//                                .typeIndicator(indicatorListCustom[cnt3])
//                                .build());
//                    }
//                    if((i+1) % (questionListCustom.size() / 3) == 0 && cnt2 < indicatorList.length-1) cnt2++;
//                }

                personalityQuestionRepository.saveAll(questionListCustom);
            }

        }
    }
}
