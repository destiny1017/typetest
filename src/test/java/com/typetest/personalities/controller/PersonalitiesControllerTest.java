package com.typetest.personalities.controller;

import com.typetest.ControllerTestSupport;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeInfo;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.dto.SessionUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PersonalitiesControllerTest extends ControllerTestSupport {

    @DisplayName("각 테스트의 메인 페이지가 리턴된다")
    @ParameterizedTest
    @ValueSource(strings = {"EXAMTEST", "CARDTEST"})
    void testPathTest(String testCode) throws Exception {
        when(testCodeInfoRepository.findById(anyString()))
                .thenReturn(createTestCodeInfo(testCode));
        mockMvc.perform(get(String.format("/%s/testMain", testCode)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(
                        "testInfo",
                        "testCode"
                ))
                .andExpect(view().name(String.format("personalities/%s-start", testCode)));
    }

    @DisplayName("각 테스트의 응답 페이지가 리턴된다")
    @ParameterizedTest
    @ValueSource(strings = {"EXAMTEST", "CARDTEST"})
    void testAnswerTest(String testCode) throws Exception {
        when(testCodeInfoRepository.findById(anyString()))
                .thenReturn(createTestCodeInfo(testCode));
        mockMvc.perform(get(String.format("/%s/testAnswer", testCode)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(
                        "testCode",
                        "questions",
                        "questionCount"
                ))
                .andExpect(view().name(String.format("personalities/%s-test", testCode)));
    }

    @DisplayName("각 테스트의 결과 페이지가 리턴된다")
    @ParameterizedTest
    @ValueSource(strings = {"EXAMTEST", "CARDTEST"})
    void testResultTest(String testCode) throws Exception {
        when(personalityTestService.createTestResultInfo(anyString(), anyString()))
                .thenReturn(createTestResultDto(testCode));
        mockMvc.perform(get(String.format("/%s/testResult/AAA", testCode)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(
                        "result",
                        "testCode"
                ))
                .andExpect(view().name(String.format("personalities/%s-result", testCode)));
    }

    @Test
    @DisplayName("테스트 응답 정보를 제출하면 결과 페이지로 redirect된다")
    void testSubmitTest() throws Exception {
        when(testCodeInfoRepository.findById(anyString()))
                .thenReturn(createTestCodeInfo("EXAMTEST"));
        when(personalityTestService.calcType(anyMap()))
                .thenReturn("AAA");

        mockMvc.perform(post("/testSubmit")
                    .param("testCode", "EXAMTEST")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("EXAMTEST/testResult/AAA"));

        verify(personalityTestService, never()).saveTestInfo(any(PersonalitiesAnswerInfo.class));
    }

    @Test
    @DisplayName("로그인한 채로 테스트 응답 정보를 제출하면 결과를 저장하고 결과 페이지로 redirect된다")
    void testSubmitTest2() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", createSessionUser());

        when(testCodeInfoRepository.findById(anyString()))
                .thenReturn(createTestCodeInfo("EXAMTEST"));
        when(personalityTestService.calcType(anyMap()))
                .thenReturn("AAA");

        mockMvc.perform(post("/testSubmit").session(session)
                        .param("testCode", "EXAMTEST")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("EXAMTEST/testResult/AAA"));

        verify(personalityTestService, times(1)).saveTestInfo(any(PersonalitiesAnswerInfo.class));
    }

    private Optional<TestCodeInfo> createTestCodeInfo(String testCode) {
        return Optional.of(TestCodeInfo.builder()
                .testCode(testCode)
                .testName("테스트1")
                .answerType(AnswerType.EXAM)
                .build());
    }

    private TestResultDto createTestResultDto(String testCode) {
        return new TestResultDto(new TypeInfo(new TestCodeInfo(), "", ""));
    }

    private SessionUser createSessionUser() {
        User user = createUser();
        return new SessionUser(user);
    }

    private User createUser() {
        return User.builder()
                .id(-1L)
                .name("Kim Daeho")
                .nickname("Diang")
                .role(Role.USER)
                .build();
    }
}