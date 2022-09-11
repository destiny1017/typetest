package com.typetest.personalities.controller;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.login.dto.SessionUser;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PersonalitiesController {

    private final PersonalityTestService personalityTestService;
    private final TestCodeInfoRepository testCodeInfoRepository;

    @GetMapping("/{testCode}/testMain")
    public String testPath(@PathVariable String testCode, Model model) {
        Optional<TestCodeInfo> testInfo = testCodeInfoRepository.findById(testCode);
        if (testInfo.isPresent()) {
            model.addAttribute("testInfo", testInfo.get());
        }
        model.addAttribute("testCode", testCode);
        return "personalities/" + testCode + "-start";
    }

    @GetMapping("/{testCode}/testAnswer")
    public String testAnswer(@PathVariable String testCode, Model model) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        model.addAttribute("testCode", testCode);
        model.addAttribute("questions", personalityTestService.getQuestions(testCode));
        model.addAttribute("questionCount", personalityTestService.getQuestionCnt(testCode));
        return "personalities/" + testCode + "-test";
    }

    @GetMapping("/testSubmit")
    public String testSubmit(@RequestParam Map<String, String> answerMapParam, HttpSession session) {
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
        answerInfo.setAnswerType(testCodeInfo.get().getAnswerType());
        answerInfo.setTestCodeInfo(testCodeInfo.get());

        // 유형 도출
        String type = personalityTestService.calcType(answerInfo);

        // 세션에 user정보 있으면 테스트결과 DB저장
        SessionUser user = (SessionUser) session.getAttribute("user");
        if(user != null) {
            answerInfo.setUserId(user.getId());
            personalityTestService.saveTestInfo(answerInfo, type);
            log.info("answerInfo = {}", answerInfo);
        } else {
            session.setAttribute("answerInfo", answerInfo);
            session.setAttribute("type", type);
        }

        return "redirect:" + testCode + "/testResult/" + type;
    }

    @GetMapping("/{testCode}/testResult/{type}")
    public String testResult(@PathVariable String testCode, @PathVariable String type, Model model) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        // 유형 결과 반환
        TestResultDto testResultDto = personalityTestService.createTestResultInfo(testCode, type);
        model.addAttribute("result", testResultDto);
        model.addAttribute("testCode", testCode);
        return "personalities/" + testCode + "-result";
    }

}
