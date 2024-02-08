package com.typetest.personalities.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.typetest.admin.testadmin.data.IndicatorSettingDto;
import lombok.Builder;
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

    private Integer cuttingPoint;

    private String result;

    @Builder
    public IndicatorSetting(Long id, TypeIndicator typeIndicator, TestCodeInfo testCode, int cuttingPoint, String result) {
        this.id = id;
        this.typeIndicator = typeIndicator;
        this.testCode = testCode;
        this.cuttingPoint = cuttingPoint;
        this.result = result;
    }

}
