package com.typetest.admin.testadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typetest.admin.testadmin.data.*;
import com.typetest.admin.testadmin.service.TestAdminService;
import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "", roles = {"ADMIN"})
class TestAdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EntityManager em;
    @Autowired
    PersonalityQuestionRepository personalityQuestionRepository;
    @Autowired
    TestCodeInfoRepository testCodeInfoRepository;
    @Autowired
    TestAdminService testAdminService;

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

        personalityQuestionRepository.saveAll(questionList);
    }

    @Test
    void testAdminPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/adminPage/testAdminPage/EXAMTEST"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("indicatorForm", "typeInfoForm", "questionForm", "testCode"));
    }

    @Test
    void step1Submit() throws Exception {
        //given
        TestInfoDto examtest = testAdminService.createTestInfoDto("EXAMTEST");
        examtest.setDescription("change Description");
        examtest.setTestName("EXAM변경");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step1Submit")
                        .flashAttr("testInfoDto", examtest)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        TestInfoDto testInfoDto = testAdminService.createTestInfoDto("EXAMTEST");

        //then
        assertThat(testInfoDto.getTestCode()).isEqualTo("EXAMTEST");
        assertThat(testInfoDto.getTestName()).isEqualTo("EXAM변경");
        assertThat(testInfoDto.getAnswerType()).isEqualTo(AnswerType.EXAM);
        assertThat(testInfoDto.getDescription()).hasSizeLessThan(20);
        assertThat(testInfoDto.getImage()).startsWith("http");
    }

    @Test
    void step2Submit() throws Exception {
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
        IndicatorForm indicatorForm = new IndicatorForm();
        indicatorForm.setIndicatorTestCode("EXAMTEST");
        indicatorForm.setIndicatorList(indicatorInfoList);

        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step2Submit")
                        .flashAttr("indicatorForm", indicatorForm)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        indicatorInfoList = testAdminService.findIndicatorInfo("EXAMTEST");

        //then
        assertThat(indicatorInfoList).hasSize(3);
        assertThat(indicatorInfoList.get(0).getIndicatorName()).isEqualTo("A지표수정");
        assertThat(indicatorInfoList.get(1).getIndicatorNum()).isEqualTo(4);
        assertThat(indicatorInfoList.get(2).getIndicatorSettings().get(0).getCuttingPoint()).isEqualTo(6);

    }

    @Test
    void step3Submit() throws Exception {
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

        QuestionForm questionForm = new QuestionForm();
        questionForm.setQuestionTestCode("EXAMTEST");
        questionForm.setQuestionList(befQuestionInfoList);

        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step3Submit")
                        .flashAttr("questionForm", questionForm)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        em.flush();
        em.clear();
        List<QuestionDto> questionInfoList = testAdminService.findQuestionInfo("EXAMTEST");

        //then
        assertThat(questionInfoList.get(0).getQuestion()).isEqualTo("질문수정");
        assertThat(questionInfoList.get(2).getQuestionImage()).isEqualTo("이미지수정");
        assertThat(questionInfoList.get(2).getAnswerList().get(0).getAnswer()).isEqualTo("답변수정");
        assertThat(questionInfoList.get(3).getAnswerList().get(1).getPoint()).isEqualTo(7);
    }

    @Test
    void step4Submit() throws Exception {
        //given
        TestCodeInfo testCode = testCodeInfoRepository.findById("EXAMTEST").get();

        TypeInfo typeInfo1 = new TypeInfo(testCode, "BCB", "비씨비");
        TypeInfo typeInfo2 = new TypeInfo(testCode, "CCB", "씨씨비");
        TypeInfo typeInfo3 = new TypeInfo(testCode, "ACC", "에씨씨");

        TypeDescription bbbDescription1 = new TypeDescription(typeInfo1, 1, "BBB description1");
        TypeImage bbbImage1 = new TypeImage(typeInfo1, 1, "url");

        typeInfo1.addDescription(bbbDescription1);
        typeInfo1.addImage(bbbImage1);

        TypeDescription bbbDescription2 = new TypeDescription(typeInfo2, 1, "CCB description1");
        TypeImage bbbImage2 = new TypeImage(typeInfo2, 1, "url");

        typeInfo2.addDescription(bbbDescription2);
        typeInfo2.addImage(bbbImage2);

        TypeDescription bbbDescription3 = new TypeDescription(typeInfo3, 1, "ACC description1");
        TypeImage bbbImage3 = new TypeImage(typeInfo3, 1, "url");

        typeInfo3.addDescription(bbbDescription3);
        typeInfo3.addImage(bbbImage3);

        List<TypeInfo> typeList = new ArrayList();

        typeList.add(typeInfo1);
        typeList.add(typeInfo2);
        typeList.add(typeInfo3);

        List<TypeInfoDto> typeInfoDtoList = typeList.stream().map(TypeInfoDto::new).collect(Collectors.toList());
        for (TypeInfoDto typeInfoDto : typeInfoDtoList) {
            typeInfoDto.setUpdated(1);
            typeInfoDto.getTypeImageList().get(0).setUpdated(1);
            typeInfoDto.getTypeDescriptionList().get(0).setUpdated(1);
            typeInfoDto.setTypeRelation(new TypeRelationDto());
        }

        TypeInfoForm typeInfoForm = new TypeInfoForm();
        typeInfoForm.setTypeInfoTestCode("EXAMTEST");
        typeInfoForm.setTypeInfoList(typeInfoDtoList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step4Submit")
                        .flashAttr("typeInfoForm", typeInfoForm)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        em.flush();
        em.clear();
        List<TypeInfoDto> findTypeInfoList = testAdminService.findTypeInfo("EXAMTEST");
        List<TypeInfoDto> newTypeList = testAdminService.findTypeInfo("NEW");

        List<String> typeCodeList = new ArrayList<>();
        List<String> typeImageList = new ArrayList<>();
        List<String> typeDescriptionList = new ArrayList<>();
        for (TypeInfoDto t : findTypeInfoList) {
            typeCodeList.add(t.getTypeCode());
            List<TypeImageDto> imgList = t.getTypeImageList();
            List<TypeDescriptionDto> descList = t.getTypeDescriptionList();
            if(imgList.size() > 0) {
                typeImageList.add(imgList.get(0).getImageUrl());
            }
            if(descList.size() > 0) {
                typeDescriptionList.add(descList.get(0).getDescription());
            }
        }

        //then
        assertThat(typeCodeList).contains(typeInfo1.getTypeCode(), typeInfo2.getTypeCode(), typeInfo3.getTypeCode());
        assertThat(typeImageList).contains(bbbImage1.getImageUrl(), bbbImage2.getImageUrl(), bbbImage3.getImageUrl());
        assertThat(typeDescriptionList).contains(bbbDescription1.getDescription(),
                bbbDescription2.getDescription(),
                bbbDescription3.getDescription());
        assertThat(newTypeList).hasSize(0);
    }

}