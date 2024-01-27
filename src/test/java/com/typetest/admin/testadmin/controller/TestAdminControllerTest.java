package com.typetest.admin.testadmin.controller;

import com.typetest.ControllerTestSupport;
import com.typetest.admin.testadmin.data.IndicatorForm;
import com.typetest.admin.testadmin.data.QuestionForm;
import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.admin.testadmin.data.TypeInfoForm;
import com.typetest.admin.testadmin.service.TestAdminService;
import com.typetest.personalities.data.AnswerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
class TestAdminControllerTest extends ControllerTestSupport {

    @MockBean
    TestAdminService testAdminService;

    @Test
    @DisplayName("테스트 정보 관리 페이지로 진입한다.")
    void testAdminPage() throws Exception {
        when(testAdminService.createTestInfoDto(anyString()))
                .thenReturn(createTestInfoDto());
        mockMvc.perform(get("/adminPage/testAdminPage/NEW"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(
                                "indicatorForm",
                                "questionForm",
                                "typeInfoForm",
                                "testCode",
                                "tab"
                        ))
                .andExpect(model().attributeDoesNotExist(
                                "essentialTypeList",
                                "alert"
                        )
                );
    }

    @Test
    @DisplayName("alert을 포함한 테스트 정보 관리 페이지로 진입한다.")
    void testAdminPage2() throws Exception {
        when(testAdminService.createTestInfoDto(anyString()))
                .thenReturn(createTestInfoDto());
        mockMvc.perform(get("/adminPage/testAdminPage/testCode/2/2"))
                .andExpect(status().isOk())
                .andExpect(model()
                        .attributeExists(
                                "essentialTypeList",
                                "indicatorForm",
                                "questionForm",
                                "typeInfoForm",
                                "testCode",
                                "tab",
                                "alert"
                        )
                );
    }

    @Test
    @DisplayName("tab1 submit시 테스트 정보 관리 페이지 tab1으로 redirect 된다.")
    void step1Submit() throws Exception {
        //given //when // then
        mockMvc.perform(post("/adminPage/testAdmin/step1Submit")
                        .flashAttr("testInfoDto", createTestInfoDto())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminPage/testAdminPage/testCode/1"));
    }

    @Test
    @DisplayName("tab2 submit시 테스트 정보 관리 페이지 tab2로 redirect 된다.")
    void step2Submit() throws Exception {
        when(testAdminService.saveIndicatorInfo(any(), any())).thenReturn(0);
        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step2Submit")
                        .flashAttr("indicatorForm", createIndicatorForm())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminPage/testAdminPage/indicatorTestCode/2"));
    }

    @Test
    @DisplayName("tab2 submit시 빈 지표정보가 있다면, 테스트 정보 관리 페이지 tab2로 del param을 포함하여 redirect 된다.")
    void step2Submit2() throws Exception {
        when(testAdminService.saveIndicatorInfo(any(), any())).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step2Submit")
                        .flashAttr("indicatorForm", createIndicatorForm())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminPage/testAdminPage/indicatorTestCode/2/2"));
    }

    @Test
    @DisplayName("tab3 submit시 테스트 정보 관리 페이지 tab3로 redirect 된다.")
    void step3Submit() throws Exception {
        QuestionForm questionForm = new QuestionForm();
        questionForm.setQuestionTestCode("testCode");
        questionForm.setQuestionList(new ArrayList<>());

        mockMvc.perform(post("/adminPage/testAdmin/step3Submit")
                        .flashAttr("questionForm", questionForm)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminPage/testAdminPage/testCode/3"));
    }

    @Test
    @DisplayName("tab4 submit시 테스트 정보 관리 페이지 tab4로 redirect 된다.")
    void step4Submit() throws Exception {
        //given
        TypeInfoForm typeInfoForm = new TypeInfoForm();
        typeInfoForm.setTypeInfoTestCode("testCode");

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/adminPage/testAdmin/step4Submit")
                        .flashAttr("typeInfoForm", typeInfoForm)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminPage/testAdminPage/testCode/4"));
    }


    private TestInfoDto createTestInfoDto() {
        return TestInfoDto.builder()
                .testCode("testCode")
                .testName("testName")
                .answerType(AnswerType.CARD)
                .active(1)
                .build();
    }

    private IndicatorForm createIndicatorForm() {
        return IndicatorForm.builder()
                .indicatorList(new ArrayList<>())
                .indicatorTestCode("indicatorTestCode")
                .build();
    }
}