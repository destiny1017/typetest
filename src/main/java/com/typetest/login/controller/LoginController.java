package com.typetest.login.controller;

import com.typetest.login.dto.SessionUser;
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

    @GetMapping(value = {"/", "/oauth2/authorization/*"})
    public String welcome(Model model, HttpServletRequest request) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        } else {
            model.addAttribute("userName", "손님");
        }
        log.error("user = " + (user != null ? user.toString() : "not found userinfo"));

        return "personalities/exam/examStart";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        return "login/login";
    }
}
