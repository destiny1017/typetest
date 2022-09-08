package com.typetest.admin.testadmin.service;

import com.typetest.admin.testadmin.data.QuestionDto;
import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.admin.testadmin.data.TypeIndicatorDto;
import com.typetest.admin.testadmin.data.TypeInfoDto;
import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import com.typetest.personalities.repository.TypeIndicatorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TestAdminServiceTest {

    @Autowired
    private TypeIndicatorRepository typeIndicatorRepository;

    @Autowired
    private PersonalityQuestionRepository personalityQuestionRepository;
    
    @Autowired
    private TestAdminService testAdminService;

    @Autowired
    private EntityManager em;

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

        personalityQuestionRepository.saveAll(questionList);
    }

    @Test
    @DisplayName("필수유형 리스트 가져오기 테스트")
    void getEssentialTypeList() {
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);

        List<String> essentialTypeList = testAdminService.getEssentialTypeList(testCodeInfo1.getTestCode());

        assertThat(essentialTypeList).hasSize(8);
        assertThat(essentialTypeList).contains("BAB", "BBA", "AAA", "ABA");
    }

    @Test @DisplayName("테스트 기본정보 불러오기 테스트")
    void createTestInfoDto() {
        //given

        //when
        TestInfoDto testInfo = testAdminService.createTestInfoDto("EXAMTEST");

        //then
        assertThat(testInfo.getTestCode()).isEqualTo("EXAMTEST");
        assertThat(testInfo.getTestName()).isEqualTo("EXAM예제");
        assertThat(testInfo.getAnswerType()).isEqualTo(AnswerType.EXAM);
        assertThat(testInfo.getDescription()).hasSizeGreaterThan(20);
        assertThat(testInfo.getImage()).startsWith("http");

    }

    @Test @DisplayName("테스트 기본정보 저장")
    void saveTestInfo() {
        //given
        TestInfoDto examtest = testAdminService.createTestInfoDto("EXAMTEST");
        examtest.setDescription("change Description");
        examtest.setTestName("EXAM변경");

        //when
        TestInfoDto testInfoDto = testAdminService.saveTestInfo(examtest);

        //then
        assertThat(testInfoDto.getTestCode()).isEqualTo("EXAMTEST");
        assertThat(testInfoDto.getTestName()).isEqualTo("EXAM변경");
        assertThat(testInfoDto.getAnswerType()).isEqualTo(AnswerType.EXAM);
        assertThat(testInfoDto.getDescription()).hasSizeLessThan(20);
        assertThat(testInfoDto.getImage()).startsWith("http");

    }

    @Test @DisplayName("지표정보 불러오기")
    void findIndicatorInfo() {
        //given
        em.clear();

        //when
        List<TypeIndicatorDto> indicatorList = testAdminService.findIndicatorInfo("EXAMTEST");

        //then
        assertThat(indicatorList).hasSize(3);
        assertThat(indicatorList.get(0).getIndicatorSettings().get(0).getResult()).isEqualTo("B");
        indicatorList.forEach(i -> assertThat(i.getIndicatorSettings()).hasSize(2));

        assertThat(indicatorList.get(1).getIndicatorNum()).isEqualTo(2);
        assertThat(indicatorList.get(2).getIndicatorName()).isEqualTo("C지표");
        assertThat(indicatorList.get(0).getIndicatorSettings().get(1).getCuttingPoint()).isEqualTo(12);
        assertThat(indicatorList.get(1).getIndicatorSettings().get(0).getResult()).isEqualTo("B");
    }

    @Test @DisplayName("지표정보 저장하기")
    void saveIndicatorInfo() {
        //given
        em.clear();
        List<TypeIndicatorDto> indicatorInfoList = testAdminService.findIndicatorInfo("EXAMTEST");

        //when
        indicatorInfoList.get(0).setIndicatorName("A지표수정");
        indicatorInfoList.get(0).setUpdated(1);
        indicatorInfoList.get(1).setIndicatorNum(4);
        indicatorInfoList.get(1).setUpdated(1);
        indicatorInfoList.get(2).getIndicatorSettings().get(0).setCuttingPoint(6);
        indicatorInfoList.get(2).getIndicatorSettings().get(0).setUpdated(1);
        testAdminService.saveIndicatorInfo(indicatorInfoList, "EXAMTEST");

        indicatorInfoList = testAdminService.findIndicatorInfo("EXAMTEST");

        //then
        assertThat(indicatorInfoList).hasSize(3);
        assertThat(indicatorInfoList.get(0).getIndicatorName()).isEqualTo("A지표수정");
        assertThat(indicatorInfoList.get(1).getIndicatorNum()).isEqualTo(4);
        assertThat(indicatorInfoList.get(2).getIndicatorSettings().get(0).getCuttingPoint()).isEqualTo(6);

    }

    @Test @DisplayName("질문정보 불러오기")
    void findQuestionInfo() {
        //given
        em.flush();
        em.clear();

        //when
        List<QuestionDto> questionInfoList = testAdminService.findQuestionInfo("EXAMTEST");

        //then
        assertThat(questionInfoList).hasSize(12);
        assertThat(questionInfoList.get(0).getAnswerList()).hasSize(5);
        assertThat(questionInfoList.get(1).getQuestion()).isEqualTo("examQuestion2");
    }

    @Test @DisplayName("질문정보 저장하기")
    void saveQuestionInfo() {
        //given
        List<QuestionDto> befQuestionInfoList = testAdminService.findQuestionInfo("EXAMTEST");

        //when
        befQuestionInfoList.get(0).setQuestion("질문수정");
        befQuestionInfoList.get(0).setUpdated(1);
        befQuestionInfoList.get(2).setQuestionImage("이미지수정");
        befQuestionInfoList.get(2).setUpdated(1);
        befQuestionInfoList.get(2).getAnswerList().get(0).setAnswer("답변수정");
        befQuestionInfoList.get(2).getAnswerList().get(0).setUpdated(1);
        befQuestionInfoList.get(3).getAnswerList().get(1).setPoint(7);
        befQuestionInfoList.get(3).getAnswerList().get(1).setUpdated(1);
        testAdminService.saveQuestionInfo(befQuestionInfoList, "EXAMTEST");
        em.flush();
        em.clear();
        List<QuestionDto> questionInfoList = testAdminService.findQuestionInfo("EXAMTEST");

        //then
        assertThat(questionInfoList.get(0).getQuestion()).isEqualTo("질문수정");
        assertThat(questionInfoList.get(2).getQuestionImage()).isEqualTo("이미지수정");
        assertThat(questionInfoList.get(2).getAnswerList().get(0).getAnswer()).isEqualTo("답변수정");
        assertThat(questionInfoList.get(3).getAnswerList().get(1).getPoint()).isEqualTo(7);

    }


//    int saveTypeInfo(List<TypeInfoDto> typeInfoDtoList, String testCode);
//    List<TypeInfoDto> findTypeInfo(String testCode);


}
