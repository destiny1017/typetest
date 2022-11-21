package com.typetest.admin.useradmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typetest.admin.useradmin.data.UserInfoDto;
import com.typetest.admin.useradmin.service.UserAdminService;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserAdminApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserAdminService userAdminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        createUser();
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 리스트 가져오기 API 호출 테스트")
    void getUserList() throws Exception {
        String uri = String.format("/api/user-admin/users");

        mvc.perform(get(uri))
                .andExpect(status().isOk());

        verify(userAdminService, only()).getUserList(any());
    }

    @Test
    @DisplayName("사용자 찾기 API 호출 테스트")
    void findUserInfo() throws Exception {
        String uri = String.format("/api/user-admin/users/%s", "test_user2");

        mvc.perform(get(uri))
                .andExpect(status().isOk());

        verify(userAdminService, only()).findUserInfo(any());
    }

    @Test
    @DisplayName("사용자 정보 업데이트 API 호출 테스트")
    void updateUserInfo() throws Exception {
        String uri = String.format("/api/user-admin/users/");

        UserInfoDto user = new UserInfoDto(userRepository.findByName("test_user1").get(0));
        user.setName("test_user4");
        String body = objectMapper.writeValueAsString(user);

        mvc.perform(put(uri).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userAdminService, only()).updateUserInfo(any());
    }

    @Test
    @DisplayName("사용자 삭제 API 호출 테스트")
    void deleteUserInfo() throws Exception {
        User user = userRepository.findByName("test_user1").get(0);
        String uri = String.format("/api/user-admin/users/%s", user.getId());

        mvc.perform(delete(uri))
                .andExpect(status().isOk());

        verify(userAdminService, only()).deleteUserInfo(any());
    }

    void createUser() {
        User user1 = User.builder()
                .name("test_user1")
                .email("test1@test.com")
                .role(Role.USER)
                .nickname("디앙1")
                .build();

        User user2 = User.builder()
                .name("test_user2")
                .email("test2@test.com")
                .role(Role.USER)
                .nickname("디앙2")
                .build();

        User user3 = User.builder()
                .name("test_user3")
                .email("test3@test.com")
                .role(Role.USER)
                .nickname("디앙3")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.flush();
    }
}