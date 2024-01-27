package com.typetest;

import com.typetest.admin.testadmin.controller.TestAdminController;
import com.typetest.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        TestAdminController.class
}, excludeAutoConfiguration = SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

}
