package com.typetest.mypage.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoDto {
    private String nickname;
    private List<TypeInfoData> typeList;

    public UserInfoDto(String nickname, List<TypeInfoData> typeList) {
        this.nickname = nickname;
        this.typeList = typeList;
    }
}
