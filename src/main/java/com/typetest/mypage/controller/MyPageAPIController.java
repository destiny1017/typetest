package com.typetest.mypage.controller;

import com.typetest.mypage.data.TypeInfoData;
import com.typetest.mypage.data.TypeInfoResponse;
import com.typetest.mypage.data.UserTypeInfoApiDto;
import com.typetest.mypage.service.MyPageService;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MyPageAPIController {

    private final MyPageService myPageService;
    private final UserRepository userRepository;

    @GetMapping("/user-type/{userEmail}")
    public TypeInfoResponse getUserTypeInfo(@PathVariable("userEmail") String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null) {
            return new TypeInfoResponse(0, null);
        }
        return new TypeInfoResponse(1, myPageService.getUserTypeInfo(user));
    }

}
