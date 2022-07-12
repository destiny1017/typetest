package com.typetest.personalities.exam.controller;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ExamController {

    private final PersonalityTestService personalityTestService;

    @GetMapping("/examStart")
    public String exam(Model model) {
        return "personalities/exam/examStart";
    }

    @GetMapping("/examTest")
    public String examTest(@ModelAttribute("PersonalitiesAnswerInfo") PersonalitiesAnswerInfo answerInfo, Model model) {
        return "personalities/exam/examTest";
    }

    @PostMapping("/examResult")
    public String examResult(@RequestBody PersonalitiesAnswerInfo answerInfo, Model model) {
        System.out.println("answerInfo = " + answerInfo);
        String type = personalityTestService.calcType(answerInfo);
        personalityTestService.saveTestInfo(answerInfo, type);
        model.addAttribute("type", type);
        return "personalities/exam/examResult";
    }
}
