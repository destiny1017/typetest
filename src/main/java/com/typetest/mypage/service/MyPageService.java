package com.typetest.mypage.service;

import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.mypage.dto.UserInfoDto;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.repository.PersonalityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final PersonalityTypeRepository personalityTypeRepository;
    private final LoginRepository loginRepository;

    public UserInfoDto getUserInfo(Long userId) {
        User user = loginRepository.findById(userId).get();
        List<TypeInfoData> typeList = personalityTypeRepository.getUserTypeList(user);
        return new UserInfoDto(user.getNickname(), typeList);
    }
}
