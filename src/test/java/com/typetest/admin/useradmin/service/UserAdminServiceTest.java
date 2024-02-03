package com.typetest.admin.useradmin.service;

import com.typetest.IntegrationTestSupport;
import com.typetest.admin.useradmin.data.UserInfoDto;
import com.typetest.exception.TypetestException;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
class UserAdminServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("전체 사용자 리스트를 페이지로 불러온다")
    void getUserList() {
        // given
        createUsers();

        // when
        Page<UserInfoDto> userList = userAdminService.getUserList(PageRequest.of(0, 10));

        // then
        assertThat(userList.getContent()).hasSize(3)
                .extracting("name", "email", "role", "nickname")
                .contains(
                        Tuple.tuple("test_user1", "test1@test.com", Role.USER, "디앙1"),
                        Tuple.tuple("test_user2", "test2@test.com", Role.USER, "디앙2"),
                        Tuple.tuple("test_user3", "test3@test.com", Role.USER, "디앙3")
                );
    }

    @Test
    @DisplayName("특정 이름에 해당하는 유저 정보들을 불러온다")
    void findUserInfo() {
        // given
        createUsers();

        // when
        List<UserInfoDto> findUser = userAdminService.findUserInfo("test_user2");

        // then
        assertThat(findUser).hasSize(1)
                .extracting("name", "email", "role", "nickname")
                .containsExactly(Tuple.tuple("test_user2", "test2@test.com", Role.USER, "디앙2"));
    }

    @Test
    @DisplayName("사용자 정보 변경시 변경 요청한 데이터만 변경된다")
    void updateUserInfo() {
        // given
        User findUser = createUser("user", "email", Role.USER, "nick");

        // when
        UserInfoDto userInfoDto = new UserInfoDto(findUser);
        userInfoDto.setEmail("updatedEmail");
        userInfoDto.setName("updatedName");
        UserInfoDto updatedUser = userAdminService.updateUserInfo(userInfoDto);

        //then
        assertThat(updatedUser).extracting("name", "email", "role", "nickname")
                .containsExactly("updatedName", "updatedEmail", Role.USER, "nick");
    }

    @Test
    @DisplayName("사용자 삭제시 해당 사용자만 삭제된다")
    void deleteUserInfo() {
        createUsers();
        User user = userRepository.findByName("test_user1").get(0);
        userAdminService.deleteUserInfo(user.getId());

        Page<User> users = userRepository.findAll(PageRequest.of(0, 10));

        assertThat(users.getContent()).hasSize(2)
                .extracting("name", "email", "role", "nickname")
                .containsExactly(
                        Tuple.tuple("test_user2", "test2@test.com", Role.USER, "디앙2"),
                        Tuple.tuple("test_user3", "test3@test.com", Role.USER, "디앙3")
                );
    }

    @Test
    @DisplayName("미존재 사용자 삭제 시도시 exception 발생")
    void deleteUserInfoFailTest() throws Exception {
        Assertions.assertThrows(TypetestException.class, () -> userAdminService.deleteUserInfo(1L));
    }

    void createUsers() {
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

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    User createUser(String name, String email, Role role, String nickname) {
        User user = User.builder()
                .name(name)
                .email(email)
                .role(role)
                .nickname(nickname)
                .build();
        return userRepository.save(user);
    }

}