package com.typetest.user.domain;

import com.typetest.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "USER_INFO")
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "user_seq_generator",
                    sequenceName = "user_seq",
                    initialValue = 1,
                    allocationSize = 1)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    private String name;
    private String email;
    private String picture;

    @ColumnDefault("'닉네임을설정해주세요'")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @CreationTimestamp
//    private LocalDateTime createDate;

    @Builder
    public User(Long id, String name, String email, String picture, Role role, String nickname){
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.nickname = nickname;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
