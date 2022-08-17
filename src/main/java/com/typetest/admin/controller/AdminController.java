package com.typetest.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/adminPage")
    public String adminPage() {
        return "admin/adminPage";
    }
}
