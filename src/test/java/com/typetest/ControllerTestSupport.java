package com.typetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typetest.admin.testadmin.controller.TestAdminController;
import com.typetest.admin.testadmin.service.TestAdminService;
import com.typetest.admin.useradmin.controller.UserAdminApiController;
import com.typetest.admin.useradmin.service.UserAdminService;
import com.typetest.config.SecurityConfig;
import com.typetest.mypage.controller.MyPageController;
import com.typetest.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        TestAdminController.class,
        UserAdminApiController.class,
        MyPageController.class
}, excludeAutoConfiguration = SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserAdminService userAdminService;

    @MockBean
    protected TestAdminService testAdminService;

    @MockBean
    protected MyPageService myPageService;

}
