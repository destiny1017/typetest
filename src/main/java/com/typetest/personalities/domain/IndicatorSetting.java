package com.typetest.personalities.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class IndicatorSetting {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TypeIndicator typeIndicator;

    private int cuttingPoint;

    private String result;

}
