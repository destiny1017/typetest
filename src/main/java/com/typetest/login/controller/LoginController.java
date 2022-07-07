package com.typetest.login.controller;

import com.typetest.login.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
        System.out.println("user = " + user.toString());
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }
}
