package com.typetest.user.controller;

import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.user.dto.SessionUser;
import com.typetest.personalities.data.PersonalitiesAnswerInfo;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final HttpSession httpSession;
    private final PersonalityTestService personalityTestService;
    private final TestCodeInfoRepository testCodeInfoRepository;

    @GetMapping(value = {"/", "/oauth2/authorization/*"})
    public String welcome(Model model, HttpServletRequest request) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
            // 로그인 후 세션에 유형 정보 있으면 저장 후 세션 데이터 삭제
            PersonalitiesAnswerInfo answerInfo = (PersonalitiesAnswerInfo) httpSession.getAttribute("answerInfo");
            if(answerInfo != null) {
                answerInfo.setUserId(user.getId());
                personalityTestService.saveTestInfo(answerInfo);
                httpSession.removeAttribute("answerInfo");
            }
        } else {
            model.addAttribute("userName", "손");
        }

        List<TestInfoDto> testInfoList = testCodeInfoRepository.findAll()
                .stream().filter(i -> i.getActive() == 1).map(TestInfoDto::new).collect(Collectors.toList());

        model.addAttribute("testInfoList", testInfoList);

        return "index";
    }
}
