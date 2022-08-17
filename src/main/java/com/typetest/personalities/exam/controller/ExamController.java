package com.typetest.personalities.exam.controller;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.login.dto.SessionUser;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import com.typetest.personalities.exam.service.ExamService;
import com.typetest.personalities.repository.TestCodeInfoRepository;
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
    private final TestCodeInfoRepository testCodeInfoRepository;

    @GetMapping("/{testCode}/testMain")
    public String examPath(@PathVariable String testCode, Model model) {
        model.addAttribute("testCode", testCode);
        return "personalities/exam/examStart";
    }

    @GetMapping("/{testCode}/testAnswer")
    public String examTest(@PathVariable String testCode, Model model) {
        model.addAttribute("testCode", testCode);
        model.addAttribute("questions", examService.getQuestions(testCode));
        model.addAttribute("questionCount", examService.getQuestionCnt(testCode));
        return "personalities/exam/examTest";
    }

//    @GetMapping("/examTestSlide")
//    public String examTestSlide(Model model) {
//        model.addAttribute("questionsSlide", examService.createQuestionSlide());
//        model.addAttribute("questionCount", examService.getQuestionSlideCnt());
//        return "personalities/exam/examTestSlide";
//    }

    @GetMapping("/examSubmit")
    public String examSubmit(@RequestParam Map<String, String> answerMapParam, HttpSession session) {
        // 테스트코드 정보 파라미터 맵에서 추출
        String testCode = answerMapParam.get("testCode");
        answerMapParam.remove("testCode");
        Optional<TestCodeInfo> testCodeInfo = testCodeInfoRepository.findById(testCode);
        if(!testCodeInfo.isPresent()) {
            throw new NotFoundEntityException("테스트 코드[" + testCode + "]에 해당하는 테스트를 찾을 수 없습니다.");
        }

        // 응답정보 객체 세팅
        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Long> answerMap = new HashMap<>();
        answerMapParam.forEach((key, value) -> answerMap.put(Integer.parseInt(key), Long.parseLong(value)));
        answerInfo.setAnswer(answerMap);
        answerInfo.setAnswerType(AnswerType.EXAM);
        answerInfo.setTestCodeInfo(testCodeInfo.get());

        // 유형 도출
        String type = personalityTestService.calcType(answerInfo);

        // 세션에 user정보 있으면 테스트결과 DB저장
        SessionUser user = (SessionUser) session.getAttribute("user");
        if(user != null) {
            answerInfo.setUserId(user.getId());
            personalityTestService.saveTestInfo(answerInfo, type);
            log.info("answerInfo = {}", answerInfo);
        }

        return "redirect:" + testCode + "/testResult/" + type;
    }

    @GetMapping("/{testCode}/testResult/{type}")
    public String examResult(@PathVariable String testCode, @PathVariable String type, Model model) {
        // 유형 결과 반환
        TestResultDto testResultDto = personalityTestService.createTestResultInfo(testCode, type);
        model.addAttribute("result", testResultDto);
        model.addAttribute("testCode", testCode);
        return "personalities/exam/examResult";
    }

}
