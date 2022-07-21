package com.typetest.personalities.exam.controller;

import com.typetest.login.dto.SessionUser;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.repository.TestCode;
import com.typetest.personalities.exam.service.ExamService;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExamController {

    private final PersonalityTestService personalityTestService;
    private final ExamService examService;

    @ModelAttribute("questions")
    public List<ExamQuestionInfo> examQuestions() {
        List<ExamQuestionInfo> questions = examService.createQuestions();
        return questions;
    }

    @GetMapping("/examStart")
    public String exam(Model model) {
        return "personalities/exam/examStart";
    }

    @GetMapping("/examTest")
    public String examTest(@ModelAttribute("personalitiesAnswerInfo") PersonalitiesAnswerInfo answerInfo, Model model) {
        return "personalities/exam/examTest";
    }

    @PostMapping("/examResult")
    public String examResult(@RequestParam HashMap<String, String> answerMapParam, Model model, HttpSession session) {
        log.info("answerMap = {}", answerMapParam);
        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Integer> answerMap = new HashMap<>();
        answerMapParam.forEach((key, value) -> answerMap.put(Integer.parseInt(key), Integer.parseInt(value)));
        answerInfo.setAnswer(answerMap);
        answerInfo.setTestCode(TestCode.EXAM);

        log.info("answerInfo = {}", answerInfo);
        String type = personalityTestService.calcType(answerInfo);
        model.addAttribute("type", type);

        SessionUser user = (SessionUser) session.getAttribute("user");
        if(user != null) {
            answerInfo.setUserId(user.getId());
            personalityTestService.saveTestInfo(answerInfo, type);
        }
        return "personalities/exam/examResult";
    }

}
