package com.typetest.personalities.exam.controller;

import com.typetest.login.dto.SessionUser;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.dto.ExamQuestionInfo;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import com.typetest.personalities.data.TestCode;
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
    public List<List<ExamQuestionInfo>> examQuestions() {
        List<List<ExamQuestionInfo>> questions = examService.createQuestions();
        return questions;
    }

    @GetMapping("/examStart")
    public String exam(Model model) {
        return "personalities/exam/examStart";
    }

    @GetMapping("/examTest")
    public String examTest(@ModelAttribute("personalitiesAnswerInfo") PersonalitiesAnswerInfo answerInfo, Model model) {
        List<List<ExamQuestionInfo>> questions = examService.createQuestions();
        model.addAttribute("questionCount", examService.getQuestionCnt());
        return "personalities/exam/examTest";
    }

    @GetMapping("/examSubmit")
    public String examSubmit(@RequestParam Map<String, String> answerMapParam, Model model, HttpSession session) {
        // 응답정보 객체 세팅
        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Integer> answerMap = new HashMap<>();
        answerMapParam.forEach((key, value) -> answerMap.put(Integer.parseInt(key), Integer.parseInt(value)));
        answerInfo.setAnswer(answerMap);
        answerInfo.setTestCode(TestCode.EXAM);

        // 유형 도출
        String type = personalityTestService.calcType(answerInfo);

        // 세션에 user정보 있으면 테스트결과 DB저장
        SessionUser user = (SessionUser) session.getAttribute("user");
        if(user != null) {
            answerInfo.setUserId(user.getId());
            personalityTestService.saveTestInfo(answerInfo, type);
        }

        return "redirect:examResult?type=" + type;
    }

    @GetMapping("/examResult")
    public String examResult(@RequestParam String type, Model model) {
        // 유형 결과 반환
        ExamResultInfo resultType = examService.getResult(type);
        model.addAttribute("result", resultType);
        return "personalities/exam/examResult";
    }

}
