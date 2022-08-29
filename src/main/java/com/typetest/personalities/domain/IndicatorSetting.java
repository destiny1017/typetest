package com.typetest.personalities.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.typetest.admin.testadmin.data.IndicatorSettingDto;
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

    public boolean checkSameValue(IndicatorSettingDto indicatorSettingDto) {
        return (this.result == null ?
                indicatorSettingDto.getResult() == null :
                indicatorSettingDto.getResult().equals(this.result))
                && this.cuttingPoint == indicatorSettingDto.getCuttingPoint();
    }

    public void updateIndicatorSetting(IndicatorSettingDto indicatorSetting) {
        this.cuttingPoint = indicatorSetting.getCuttingPoint();
        this.result = indicatorSetting.getResult();
    }

    public IndicatorSetting(TypeIndicator typeIndicator, TestCodeInfo testCode, int cuttingPoint, String result) {
        this.typeIndicator = typeIndicator;
        this.testCode = testCode;
        this.cuttingPoint = cuttingPoint;
        this.result = result;
    }

    public IndicatorSetting(TypeIndicator typeIndicator, TestCodeInfo testCode, IndicatorSettingDto indicatorSettingDto) {
        this.id = indicatorSettingDto.getId();
        this.typeIndicator = typeIndicator;
        this.testCode = testCode;
        this.cuttingPoint = indicatorSettingDto.getCuttingPoint();
        this.result = indicatorSettingDto.getResult();
    }
}
