package com.typetest.mypage.controller;

import com.typetest.login.domain.User;
import com.typetest.login.dto.SessionUser;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.mypage.service.MyPageService;
import com.typetest.personalities.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @RequestMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if(sessionUser != null) {
            User user = User.builder().id(sessionUser.getId()).build();
            Map<String, TypeInfoData> userTypeMap = myPageService.getUserTypeInfo(user);
            model.addAttribute("userTypeMap", userTypeMap);
            return "mypage/myPage";
        }
        return "index";
    }

    @PostMapping("/myPage/editNickname")
    public String editNickname(@RequestParam("id") Long id, @RequestParam("nickname") String nickname, HttpSession session) {
        User user = myPageService.updateNickname(id, nickname);
        SessionUser sessionUser = new SessionUser(user);
        session.setAttribute("user", sessionUser);
        return "mypage/myPage";
    }
}
