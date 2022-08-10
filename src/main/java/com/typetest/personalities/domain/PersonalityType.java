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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private String typeCode;

    @CreationTimestamp
    private LocalDateTime createDate;

    public PersonalityType(User user, TestCodeInfo testCode, String typeCode) {
        this.user = user;
        this.testCode = testCode;
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return "PersonalityType{" +
                "id=" + id +
                ", type='" + typeCode + '\'' +
                '}';
    }
}
