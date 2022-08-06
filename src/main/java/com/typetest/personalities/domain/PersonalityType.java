package com.typetest.personalities.domain;

import com.typetest.login.domain.User;
import com.typetest.personalities.data.AnswerType;
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
    private AnswerType answerType;

    private String typeCode;

    @CreationTimestamp
    private LocalDateTime createDate;

    public PersonalityType(User user, AnswerType answerType, String typeCode) {
        this.user = user;
        this.answerType = answerType;
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return "PersonalityType{" +
                "id=" + id +
                ", testCode=" + answerType +
                ", type='" + typeCode + '\'' +
                '}';
    }
}
