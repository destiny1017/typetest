package com.typetest.admin.useradmin.controller;

import com.typetest.admin.useradmin.data.UserInfoDto;
import com.typetest.admin.useradmin.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAdminApiController {

    private final UserAdminService userAdminService;

    @GetMapping("/api/user-admin/users")
    public Page<UserInfoDto> getUserList(Pageable pageable) {
        return userAdminService.getUserList(pageable);
    }

    @GetMapping("/api/user-admin/users/{name}")
    public List<UserInfoDto> findUserInfo(@PathVariable String name) {
        return userAdminService.findUserInfo(name);
    }

    @PutMapping("/api/user-admin/users")
    public UserInfoDto updateUserInfo(@RequestBody UserInfoDto userInfoDto) {
        return userAdminService.updateUserInfo(userInfoDto);
    }

    @DeleteMapping("/api/user-admin/users/{id}")
    public void deleteUserInfo(@PathVariable Long id) {
        userAdminService.deleteUserInfo(id);
    }

}
