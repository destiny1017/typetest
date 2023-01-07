package com.typetest.admin.useradmin.controller;

import com.typetest.admin.useradmin.data.UserInfoDto;
import com.typetest.admin.useradmin.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAdminApiController {

    private final UserAdminService userAdminService;

    @GetMapping("/api/user-admin/users")
    public ResponseEntity getUserList(Pageable pageable) {
        Page<UserInfoDto> userList = userAdminService.getUserList(pageable);
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @GetMapping("/api/user-admin/users/{name}")
    public ResponseEntity findUserInfo(@PathVariable String name) {
        List<UserInfoDto> userInfo = userAdminService.findUserInfo(name);
        return new ResponseEntity(userInfo, HttpStatus.OK);
    }

    @PutMapping("/api/user-admin/users")
    public ResponseEntity updateUserInfo(@RequestBody UserInfoDto userInfoDto) {
        UserInfoDto updatedUserInfoDto = userAdminService.updateUserInfo(userInfoDto);
        return new ResponseEntity(updatedUserInfoDto, HttpStatus.OK);
    }

    @DeleteMapping("/api/user-admin/users/{id}")
    public ResponseEntity deleteUserInfo(@PathVariable Long id) {
        userAdminService.deleteUserInfo(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
