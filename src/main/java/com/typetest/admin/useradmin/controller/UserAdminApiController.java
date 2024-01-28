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
    public ResponseEntity<Page<UserInfoDto>> getUserList(Pageable pageable) {
        Page<UserInfoDto> userListPage = userAdminService.getUserList(pageable);
        return ResponseEntity.ok(userListPage);
    }

    @GetMapping("/api/user-admin/users/{name}")
    public ResponseEntity<List<UserInfoDto>> findUserInfo(@PathVariable String name) {
        List<UserInfoDto> userInfo = userAdminService.findUserInfo(name);
        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/api/user-admin/users")
    public ResponseEntity<UserInfoDto> updateUserInfo(@RequestBody UserInfoDto userInfoDto) {
        UserInfoDto updatedUserInfoDto = userAdminService.updateUserInfo(userInfoDto);
        return ResponseEntity.ok(updatedUserInfoDto);
    }

    @DeleteMapping("/api/user-admin/users/{id}")
    public ResponseEntity deleteUserInfo(@PathVariable Long id) {
        userAdminService.deleteUserInfo(id);
        return ResponseEntity.ok().build();
    }

}
