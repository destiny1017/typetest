package com.typetest;

import com.typetest.admin.testadmin.controller.TestAdminController;
import com.typetest.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        TestAdminController.class
}, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityConfig.class
        )
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

}
