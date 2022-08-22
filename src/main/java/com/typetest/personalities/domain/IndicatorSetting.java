package com.typetest.personalities.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class IndicatorSetting {

    @Id @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TypeIndicator typeIndicator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private int cuttingPoint;

    private String result;

    public IndicatorSetting(TypeIndicator typeIndicator, TestCodeInfo testCode, int cuttingPoint, String result) {
        this.typeIndicator = typeIndicator;
        this.testCode = testCode;
        this.cuttingPoint = cuttingPoint;
        this.result = result;
    }
}
