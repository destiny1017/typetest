package com.typetest.admin.useradmin.service;

import com.typetest.admin.useradmin.data.UserInfoDto;
import com.typetest.constant.ErrorCode;
import com.typetest.exception.NotFoundEntityException;
import com.typetest.exception.TypetestException;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    public Page<UserInfoDto> getUserList(Pageable pageable) {
        List<User> userList = userRepository.findAll();
        return new PageImpl<UserInfoDto>(
                userList.stream()
                        .map(UserInfoDto::new)
                        .collect(Collectors.toList()), pageable, userList.size());
    }

    public List<UserInfoDto> findUserInfo(String name) {
        return userRepository.findByName(name)
                .stream().map(UserInfoDto::new).collect(Collectors.toList());
    }

    public UserInfoDto updateUserInfo(UserInfoDto userInfoDto) {
        User user = User.builder()
                .id(userInfoDto.getId())
                .name(userInfoDto.getName())
                .email(userInfoDto.getEmail())
                .picture(userInfoDto.getPicture())
                .role(userInfoDto.getRole())
                .nickname(userInfoDto.getNickname())
                .build();
        return new UserInfoDto(userRepository.save(user));
    }

    public void deleteUserInfo(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
          throw new TypetestException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
