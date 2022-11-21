package com.typetest.admin.useradmin.data;

import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class UserInfoDto {

    private Long id;
    private String name;
    private String email;
    private String picture;
    private String nickname;
    private Role role;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
