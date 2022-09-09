package com.typetest.login.controller;

import com.typetest.login.dto.SessionUser;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final HttpSession httpSession;
    private final PersonalityTestService personalityTestService;

    @GetMapping(value = {"/", "/oauth2/authorization/*"})
    public String welcome(Model model, HttpServletRequest request) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
            // 로그인 후 세션에 유형 정보 있으면 저장 후 세션 데이터 삭제
            String type = (String) httpSession.getAttribute("type");
            PersonalitiesAnswerInfo answerInfo = (PersonalitiesAnswerInfo) httpSession.getAttribute("answerInfo");
            if(type != null && answerInfo != null) {
                answerInfo.setUserId(user.getId());
                personalityTestService.saveTestInfo(answerInfo, type);
                httpSession.removeAttribute("type");
                httpSession.removeAttribute("answerInfo");
            }
        } else {
            model.addAttribute("userName", "손");
        }
        log.error("user = " + (user != null ? user.toString() : "not found userinfo"));


        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        return "login/login";
    }
}
