package com.typetest.login.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "USER_INFO")
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String picture;

    @Builder
    public User(String name, String email, String picture/*, Role role */){
        this.name = name;
        this.email = email;
        this.picture = picture;
//        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }
}
