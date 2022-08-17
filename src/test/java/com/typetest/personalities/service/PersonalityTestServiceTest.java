package com.typetest.personalities.service;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.repository.TestResultDetailRepository;
import com.typetest.personalities.repository.TestResultRepository;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import com.typetest.personalities.repository.TypeInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonalityTestServiceTest {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
//    @Qualifier("personalityTestServiceImpl")
    private PersonalityTestService personalityTestService;

    @Autowired
    private TestResultDetailRepository ptdRepository;

    @Autowired
    private TestResultRepository ptRepository;

    @Autowired
    TestCodeInfoRepository testCodeInfoRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("사용자 선택값으로 유형 결과 도출하는 로직 테스트")
    public void calcTypeTest() throws Exception {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);

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

        em.persist(testCodeInfo1);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(indicatorSetting1);
        em.persist(indicatorSetting2);
        em.persist(indicatorSetting3);
        em.persist(indicatorSetting4);
        em.persist(indicatorSetting5);
        em.persist(indicatorSetting6);
        

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

        questionList.stream().forEach(entity -> em.persist(entity));
        em.flush();

        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Long> answer = new HashMap<>();

        answer.put(1, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(2, questionList.get(1).getAnswerList().get(0).getId());
        answer.put(3, questionList.get(2).getAnswerList().get(0).getId());
        answer.put(4, questionList.get(3).getAnswerList().get(0).getId());
        answer.put(5, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(6, questionList.get(5).getAnswerList().get(2).getId());
        answer.put(7, questionList.get(6).getAnswerList().get(2).getId());
        answer.put(8, questionList.get(7).getAnswerList().get(2).getId());
        answer.put(9,  questionList.get(8).getAnswerList().get(4).getId());
        answer.put(10, questionList.get(9).getAnswerList().get(4).getId());
        answer.put(11, questionList.get(10).getAnswerList().get(4).getId());
        answer.put(12, questionList.get(11).getAnswerList().get(4).getId());

        answerInfo.setUserId(user.getId());
        answerInfo.setAnswerType(AnswerType.EXAM);
        answerInfo.setAnswer(answer);
        answerInfo.setTestCodeInfo(testCodeInfo1);

        //when
        String type = personalityTestService.calcType(answerInfo);

        //then
        assertThat(type).hasSize(3);
        assertThat(type).isEqualTo("BBA");
    }

    @Test
    @DisplayName("사용자 유형테스트 결과 저장 테스트")
    void saveInfoTest() {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo = new TypeInfo(testCodeInfo1, "BBB", "비비비");

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

        em.persist(testCodeInfo1);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(indicatorSetting1);
        em.persist(indicatorSetting2);
        em.persist(indicatorSetting3);
        em.persist(indicatorSetting4);
        em.persist(indicatorSetting5);
        em.persist(indicatorSetting6);

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
        questionList.stream().forEach(entity -> em.persist(entity));

        em.persist(user);
        em.persist(testCodeInfo1);
        em.persist(typeInfo);

        em.flush();

        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Long> answer = new HashMap<>();

        answer.put(1, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(2, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(3, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(4, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(5, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(6, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(7, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(8, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(9,  questionList.get(8).getAnswerList().get(4).getId());
        answer.put(10, questionList.get(8).getAnswerList().get(4).getId());
        answer.put(11, questionList.get(8).getAnswerList().get(4).getId());
        answer.put(12, questionList.get(8).getAnswerList().get(4).getId());

        answerInfo.setUserId(user.getId());
        answerInfo.setAnswerType(AnswerType.EXAM);
        answerInfo.setAnswer(answer);
        answerInfo.setTestCodeInfo(testCodeInfo1);

        //when
        personalityTestService.saveTestInfo(answerInfo, typeInfo.getTypeCode());

        //then
        TestResult testResult = ptRepository.findByUser(user).get(0);
        List<TestResultDetail> testResultDetails = ptdRepository.findByUserAndTestCode(user, testCodeInfo1);

        assertThat(testResult.getTypeInfo().getTypeCode()).isEqualTo("BBB");
        assertThat(testResult.getTypeInfo()).isEqualTo(typeInfo);
        assertThat(testResult.getUser()).isEqualTo(user);
        assertThat(testResult.getTestCode()).isEqualTo(testCodeInfo1);

        assertThat(testResultDetails).hasSize(12);
    }

    @Test
    @DisplayName("테스트 결과 DTO 생성 서비스 테스트")
    void createTestResultInfoTest() {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
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

        TypeDescription bbaDescription1 = new TypeDescription(typeInfo1, 1, "BBA description1");
        TypeDescription aaaDescription1 = new TypeDescription(typeInfo2, 1, "AAA description1");

        TypeImage bbaImage1 = new TypeImage(typeInfo1, 1, "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200");
        TypeImage aaaImage1 = new TypeImage(typeInfo2, 1, "https://post-phinf.pstatic.net/MjAyMDExMDVfMyAg/MDAxNjA0NTY2NjIwNTc1.8jOap6uQdNegKLE8UXA5xrYo0sYRfOGlCb4W5vPI_3Ag.uPwZ8ljqoThpaUFcjnH-L61oLScNgvLJGJ7J5i-gl3wg.PNG/2.png?type=w1200");

        typeInfo2.addDescription(aaaDescription1);
        typeInfo1.addDescription(bbaDescription1);

        typeInfo2.addImage(aaaImage1);
        typeInfo1.addImage(bbaImage1);

        em.persist(user);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(typeInfo5);

        em.persist(testCodeInfo1);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(indicatorSetting1);
        em.persist(indicatorSetting2);
        em.persist(indicatorSetting3);
        em.persist(indicatorSetting4);
        em.persist(indicatorSetting5);
        em.persist(indicatorSetting6);

//        em.persist(aaaDescription1);
//        em.persist(bbaDescription1);
//
//        em.persist(aaaImage1);
//        em.persist(bbaImage1);

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

        questionList.stream().forEach(entity -> em.persist(entity));
        em.flush();

        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Long> answer = new HashMap<>();

        answer.put(1, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(2, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(3, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(4, questionList.get(0).getAnswerList().get(0).getId());
        answer.put(5, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(6, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(7, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(8, questionList.get(4).getAnswerList().get(2).getId());
        answer.put(9,  questionList.get(8).getAnswerList().get(4).getId());
        answer.put(10, questionList.get(8).getAnswerList().get(4).getId());
        answer.put(11, questionList.get(8).getAnswerList().get(4).getId());
        answer.put(12, questionList.get(8).getAnswerList().get(4).getId());

        answerInfo.setUserId(user.getId());
        answerInfo.setAnswerType(AnswerType.EXAM);
        answerInfo.setAnswer(answer);
        answerInfo.setTestCodeInfo(testCodeInfo1);



        //when
        TestResultDto testResultDto = personalityTestService.createTestResultInfo("EXAMTEST", "BBA");

        //then
        assertThat(testResultDto.getTypeCode()).isEqualTo("BBA");
        assertThat(testResultDto.getTypeName()).isEqualTo("비비에이");
        assertThat(testResultDto.getDescriptions()).hasSize(1);
        assertThat(testResultDto.getImages()).hasSize(1);
    }
}