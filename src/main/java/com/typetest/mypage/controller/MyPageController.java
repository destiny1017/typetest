package com.typetest.mypage.controller;

import com.typetest.login.domain.User;
import com.typetest.login.dto.SessionUser;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.mypage.service.MyPageService;
import com.typetest.personalities.data.TestCode;
import com.typetest.personalities.repository.PersonalityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final PersonalityTypeRepository personalityTypeRepository;
    private final MyPageService myPageService;

    @RequestMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if(sessionUser != null) {
            User user = new User();
            user.setId(sessionUser.getId());
//            List<TypeInfoData> userTypeList = personalityTypeRepository.getUserTypeList(user);
            Map<TestCode, TypeInfoData> userTypeMap = myPageService.getUserTypeInfo(user);
            model.addAttribute("userTypeMap", userTypeMap);
            return "mypage/myPage";
        }
        return "login/login";
    }
}
