package com.typetest.personalities.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TypeIndicator {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private int indicatorNum;

    private String indicatorName;

    @OneToMany(mappedBy = "typeIndicator", cascade = CascadeType.ALL)
    private List<IndicatorSetting> indicatorSettings = new ArrayList<>();

    public TypeIndicator(TestCodeInfo testCode, int indicatorNum, String indicatorName) {
        this.testCode = testCode;
        this.indicatorNum = indicatorNum;
        this.indicatorName = indicatorName;
    }
}
