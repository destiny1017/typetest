package com.typetest.admin.testadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TestAdminController {

    @GetMapping("/testAdminPage")
    public String testAdminPage() {
        return "admin/testadmin/testAdminPage";
    }

    @GetMapping("/testAdminPage/{testCode}")
    public String testAdminPage(@PathVariable String testCode, Model model) {
        model.addAttribute("testInfo", "");
        return "admin/testadmin/testAdminPage";
    }
}
