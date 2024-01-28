package com.typetest.admin.useradmin.controller;

import com.typetest.ControllerTestSupport;
import com.typetest.admin.useradmin.data.UserInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserAdminApiControllerTest extends ControllerTestSupport {


    @Test
    @DisplayName("사용자 리스트 가져오기 API 호출 테스트")
    void getUserList() throws Exception {

        when(userAdminService.getUserList(any())).thenReturn(any());
        mockMvc.perform(get("/api/user-admin/users"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userAdminService, only()).getUserList(any());
    }

    @Test
    @DisplayName("사용자 찾기 API 호출 테스트")
    void findUserInfo() throws Exception {
        when(userAdminService.findUserInfo(anyString())).thenReturn(any());
        mockMvc.perform(get("/api/user-admin/users/userName"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userAdminService, only()).findUserInfo(any());
    }

    @Test
    @DisplayName("사용자 정보 업데이트 API 호출 테스트")
    void updateUserInfo() throws Exception {
        when(userAdminService.updateUserInfo(any(UserInfoDto.class))).thenReturn(any(UserInfoDto.class));
        mockMvc.perform(put("/api/user-admin/users")
                        .content(objectMapper.writeValueAsString(new UserInfoDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userAdminService, only()).updateUserInfo(any());
    }

    @Test
    @DisplayName("사용자 삭제 API 호출 테스트")
    void deleteUserInfo() throws Exception {
        doNothing().when(userAdminService).deleteUserInfo(anyLong());
        mockMvc.perform(delete("/api/user-admin/users/-1"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userAdminService, times(1)).deleteUserInfo(anyLong());
    }

}