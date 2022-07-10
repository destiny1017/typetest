package com.typetest.personalities.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ExamController {

    @GetMapping("/examStart")
    public String exam(Model model) {
        return "personalities/exam/examStart";
    }

    @GetMapping("/examTest")
    public String examTest(Model model) {
        return "personalities/exam/examTest";
    }

    @GetMapping("/examResult")
    public String examResult(Model model) {
        return "personalities/exam/examResult";
    }
}
