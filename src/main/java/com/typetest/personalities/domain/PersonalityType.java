package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.data.TestCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityType {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    @Enumerated(EnumType.STRING)
    private TestCode testCode;

    private String type;

    @CreationTimestamp
    private LocalDateTime createDate;

    public PersonalityType(User user, TestCode testCode, String type) {
        this.user = user;
        this.testCode = testCode;
        this.type = type;
    }

    @Override
    public String toString() {
        return "PersonalityType{" +
                "id=" + id +
                ", testCode=" + testCode +
                ", type='" + type + '\'' +
                '}';
    }
}
