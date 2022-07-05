package com.typetest.login.service;

import com.typetest.login.domain.User;

import java.util.Map;

public interface LoginService {
    int login(Map<String, String> loginInfo);
    int logout(String userId);
    User createUser(Map<String, String> userInfo);
}
