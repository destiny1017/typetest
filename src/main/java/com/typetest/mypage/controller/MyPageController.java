package com.typetest.mypage.controller;

import com.typetest.login.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @RequestMapping("/myPage")
    public String myPage(HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        if(user != null) {
            Long userId = user.getId();

            return "mypage/myPage";
        }
        return "login/login";
    }
}
