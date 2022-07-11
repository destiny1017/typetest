package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.exam.repository.TestCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @ToString
@NoArgsConstructor
public class PersonalityTypeDetail {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    private TestCode testCode;

    private int answer;

    public PersonalityTypeDetail(User user, TestCode testCode, int answer) {
        this.user = user;
        this.testCode = testCode;
        this.answer = answer;
    }

}
