package com.typetest.login.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class User {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    private String name;
    private String email;
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createDate;

    @Builder
    public User(String name, String email, String picture, Role role ){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
