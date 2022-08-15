package com.typetest.personalities.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class TypeIndicator {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private int indicatorNum;

    private String indicatorName;

    public TypeIndicator(TestCodeInfo testCode, int indicatorNum, String indicatorName) {
        this.testCode = testCode;
        this.indicatorNum = indicatorNum;
        this.indicatorName = indicatorName;
    }
}
