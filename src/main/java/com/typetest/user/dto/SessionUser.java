package com.typetest.user.dto;

import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Getter
@ToString
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private String nickname;
    private Role role;

    public SessionUser(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
