package com.typetest.login.service;

import com.typetest.login.domain.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuthLoginService implements LoginService {

    @Override
    public int login(Map<String, String> loginInfo) {
        return 0;
    }

    @Override
    public int logout(String userId) {
        return 0;
    }

    @Override
    public User createUser(Map<String, String> userInfo) {
        return null;
    }
}
