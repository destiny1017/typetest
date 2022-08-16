package com.typetest.personalities.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class IndicatorSetting {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TypeIndicator typeIndicator;

    private int cuttingPoint;

    private String result;

    public IndicatorSetting(TypeIndicator typeIndicator, int cuttingPoint, String result) {
        this.typeIndicator = typeIndicator;
        this.cuttingPoint = cuttingPoint;
        this.result = result;
    }
}
