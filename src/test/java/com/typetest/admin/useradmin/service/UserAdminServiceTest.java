package com.typetest.admin.useradmin.service;

import com.typetest.admin.useradmin.data.UserInfoDto;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserAdminServiceTest {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 리스트 가져오기 테스트")
    void getUserList() {
        createUser();
        Page<UserInfoDto> userList = userAdminService.getUserList(PageRequest.of(0, 10));
        assertThat(userList.getContent().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("사용자 정보 찾기 테스트")
    void findUserInfo() {
        createUser();
        List<UserInfoDto> findUser = userAdminService.findUserInfo("test_user2");
        assertThat(findUser).hasSize(1);
        assertThat(findUser.get(0).getName()).isEqualTo("test_user2");
    }

    @Test
    @DisplayName("사용자 정보 변경 테스트")
    void updateUserInfo() {
        createUser();
        User findUser = userRepository.findByName("test_user3").get(0);
        UserInfoDto userInfoDto = new UserInfoDto(findUser);
        userInfoDto.setEmail("updated");
        userInfoDto.setName("test_user4");
        UserInfoDto updatedUser = userAdminService.updateUserInfo(userInfoDto);

        assertThat(updatedUser.getEmail()).isEqualTo("updated");
        assertThat(updatedUser.getName()).isEqualTo("test_user4");
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void deleteUserInfo() {
        createUser();
        User user = userRepository.findByName("test_user1").get(0);
        userAdminService.deleteUserInfo(user.getId());

        Page<User> users = userRepository.findAll(PageRequest.of(0, 10));

        assertThat(users.getContent().size()).isEqualTo(2);
    }

    void createUser() {
        User user1 = User.builder()
                .name("test_user1")
                .email("test1@test.com")
                .role(Role.USER)
                .nickname("디앙1")
                .build();

        User user2 = User.builder()
                .name("test_user2")
                .email("test2@test.com")
                .role(Role.USER)
                .nickname("디앙2")
                .build();

        User user3 = User.builder()
                .name("test_user3")
                .email("test3@test.com")
                .role(Role.USER)
                .nickname("디앙3")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.flush();
    }

}