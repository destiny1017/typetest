package com.typetest.mypage.controller;

import com.typetest.ControllerTestSupport;
import com.typetest.personalities.data.UserTendencyInfo;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.dto.SessionUser;
import com.typetest.user.repository.UserRepository;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MyPageControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("비로그인상태로 마이페이지 진입 시 메인으로 redirect 된다")
    void myPageGuestTest() throws Exception {
        mockMvc.perform(get("/myPage"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인상태로 마이페이지 진입 시 정상적으로 마이페이지 진입")
    void myPageMemberTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", createSessionUser());

        when(myPageService.getUserTypeInfo(any(User.class)))
                .thenReturn(new HashMap<>());
        when(myPageService.getUserTendencyInfo(any(User.class)))
                .thenReturn(new UserTendencyInfo(new HashMap<>()));

        mockMvc.perform(get("/myPage").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userTypeMap", "userTendencyInfo"));
    }

    @Test
    @DisplayName("사용자 닉네임 업데이트 요청 시 작업 후 마이페이지로 리턴된다")
    void editNicknameTest() throws Exception {
        when(myPageService.updateNickname(anyLong(), anyString()))
                .thenReturn(createUser());

        mockMvc.perform(post("/myPage/editNickname")
                .param("id", "-1")
                .param("nickname", "name"))
                .andExpect(status().isOk());
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