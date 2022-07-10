package com.typetest.login.controller;

import com.typetest.login.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final HttpSession httpSession;

    @GetMapping(value = {"/", "/oauth2/authorization/*"})
    public String welcome(Model model) {
//        model.addAttribute("posts", postsService.findAll());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        } else {
            model.addAttribute("userName", "손님");
        }
        log.error("user = " + (user != null ? user.toString() : "not found userinfo"));

        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        return "login/login";
    }
}
