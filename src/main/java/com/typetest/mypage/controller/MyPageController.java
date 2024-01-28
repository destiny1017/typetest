package com.typetest.mypage.controller;

import com.typetest.personalities.data.UserTendencyInfo;
import com.typetest.user.domain.User;
import com.typetest.user.dto.SessionUser;
import com.typetest.mypage.data.TypeInfoData;
import com.typetest.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            UserTendencyInfo userTendencyInfo = myPageService.getUserTendencyInfo(user);
            model.addAttribute("userTypeMap", userTypeMap);
            model.addAttribute("userTendencyInfo", userTendencyInfo);
            return "mypage/myPage";
        }
        return "redirect:/";
    }

    @PostMapping("/myPage/editNickname")
    public String editNickname(@RequestParam("id") Long id, @RequestParam("nickname") String nickname, HttpSession session) {
        User user = myPageService.updateNickname(id, nickname);
        SessionUser sessionUser = new SessionUser(user);
        session.setAttribute("user", sessionUser);
        return "mypage/myPage";
    }

}
