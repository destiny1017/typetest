package com.typetest.personalities.exam.controller;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ExamController {

    @GetMapping("/examStart")
    public String exam(Model model) {
        return "personalities/exam/examStart";
    }

    @GetMapping("/examTest")
    public String examTest(@ModelAttribute("PersonalitiesAnswerInfo") PersonalitiesAnswerInfo answerInfo, Model model) {
        return "personalities/exam/examTest";
    }

    @PostMapping("/examResult")
    public String examResult(@RequestParam("answerInfo") Map<String, Integer> answerInfo, Model model) {
        System.out.println("answerInfo = " + answerInfo);
        return "personalities/exam/examResult";
    }
}
