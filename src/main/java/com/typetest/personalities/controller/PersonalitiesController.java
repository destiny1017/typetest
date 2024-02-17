package com.typetest.personalities.controller;

import com.typetest.common.constant.ErrorCode;
import com.typetest.common.exception.TypetestException;
import com.typetest.personalities.data.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.TestResultDto;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import com.typetest.personalities.service.PersonalityTestService;
import com.typetest.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
        TestCodeInfo testInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));
        model.addAttribute("testInfo", testInfo);
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

    @PostMapping("/testSubmit")
    public String testSubmit(@RequestParam Map<String, String> answerMapParam, HttpSession session) {
        // 테스트코드 정보 파라미터 맵에서 추출
        String testCode = answerMapParam.remove("testCode");
        TestCodeInfo testCodeInfo = testCodeInfoRepository.findById(testCode)
                .orElseThrow(() -> new TypetestException(ErrorCode.NOT_FOUND_ENTITY, testCode));

        // 응답정보 객체 세팅
        PersonalitiesAnswerInfo answerInfo = PersonalitiesAnswerInfo.of(answerMapParam, testCodeInfo);

        // 유형 도출
        String type = personalityTestService.calcType(answerInfo.getAnswer());
        answerInfo.setType(type);

        // 해당 테스트 및 유형 play/result 카운트 +1 증가
        personalityTestService.plusResultCount(testCodeInfo, type);

        // 세션에 user정보 있으면 테스트결과 DB저장
        SessionUser user = (SessionUser) session.getAttribute("user");
        if(user != null) {
            answerInfo.setUserId(user.getId());
            personalityTestService.saveTestInfo(answerInfo);
        } else {
            // 비회원이면 결과 데이터 세션에 저장
            session.setAttribute("answerInfo", answerInfo);
        }

        return "redirect:" + testCode + "/testResult/" + type;
    }

    @GetMapping("/{testCode}/testResult/{type}")
    public String testResult(@PathVariable String testCode, @PathVariable String type, Model model) {
        TestResultDto testResultDto = personalityTestService.createTestResultInfo(testCode, type);
        model.addAttribute("result", testResultDto);
        model.addAttribute("testCode", testCode);
        return "personalities/" + testCode + "-result";
    }

}
