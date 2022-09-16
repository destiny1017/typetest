package com.typetest.admin.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("/adminPage")
    @WithMockUser(username = "", roles = {"ADMIN"})
    void adminPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/adminPage"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/loginPage")
    void loginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/loginPage"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}