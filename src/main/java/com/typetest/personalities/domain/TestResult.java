package com.typetest.personalities.domain;

import com.typetest.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class TestResult {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TYPE_INFO")
    private TypeInfo typeInfo;

    @CreationTimestamp
    private LocalDateTime createDate;

    public TestResult(User user, TestCodeInfo testCode, TypeInfo typeInfo) {
        this.user = user;
        this.testCode = testCode;
        this.typeInfo = typeInfo;
    }

    @Builder
    public TestResult(User user, TestCodeInfo testCode, TypeInfo typeInfo, LocalDateTime createDate) {
        this.user = user;
        this.testCode = testCode;
        this.typeInfo = typeInfo;
        this.createDate = createDate;
    }
}
