package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeIndicatorDto;
import lombok.Builder;
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

    private Integer indicatorNum;

    private String indicatorName;

    @OneToMany(mappedBy = "typeIndicator", cascade = CascadeType.ALL)
    private List<IndicatorSetting> indicatorSettings = new ArrayList<>();

    @OneToMany(mappedBy = "typeIndicator", cascade = CascadeType.ALL)
    private List<PersonalityAnswer> answerList = new ArrayList<>();

    @Builder
    public TypeIndicator(Long id, TestCodeInfo testCode, Integer indicatorNum, String indicatorName, List<IndicatorSetting> indicatorSettings, List<PersonalityAnswer> answerList) {
        this.id = id;
        this.testCode = testCode;
        this.indicatorNum = indicatorNum;
        this.indicatorName = indicatorName;
        this.indicatorSettings = indicatorSettings;
        this.answerList = answerList;
    }

    public TypeIndicator(TestCodeInfo testCode, int indicatorNum, String indicatorName) {

        this.testCode = testCode;
        this.indicatorNum = indicatorNum;
        this.indicatorName = indicatorName;
    }

}
