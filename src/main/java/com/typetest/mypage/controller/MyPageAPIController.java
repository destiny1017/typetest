package com.typetest.mypage.controller;

import com.typetest.mypage.data.TypeInfoData;
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

    @GetMapping("/api/getUserTypeInfo/{userEmail}")
    public TypeInfoResponse getUserTypeInfo(@PathVariable("userEmail") String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()) {
            return new TypeInfoResponse(1, myPageService.getUserTypeInfo(user.get()));
        } else {
            return new TypeInfoResponse(0, null);
        }
    }

    @Data
    class TypeInfoResponse {
        private int returnCode;
        private HashMap<String, UserTypeInfoApiDto> data = new HashMap<>();

        public TypeInfoResponse(int returnCode, Map<String, TypeInfoData> typeInfoData) {
            this.returnCode = returnCode;
            if(typeInfoData != null) {
                typeInfoData.forEach((k, v) -> data.put(k, new UserTypeInfoApiDto(v)));
            }
        }
    }

}
