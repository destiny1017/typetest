package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.exam.repository.TestCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @ToString
@NoArgsConstructor
public class PersonalityType {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    private TestCode testCode;

    private String type;

    public PersonalityType(User user, TestCode testCode, String type) {
        this.user = user;
        this.testCode = testCode;
        this.type = type;
    }
}
