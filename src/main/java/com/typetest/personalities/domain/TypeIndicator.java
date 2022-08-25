package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeIndicatorDto;
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

    public void updateIndicatorInfo(TypeIndicatorDto indicatorDto) {
        this.indicatorNum = indicatorDto.getIndicatorNum();
        this.indicatorName = indicatorDto.getIndicatorName();
    }

    public boolean checkSameValue(TypeIndicatorDto indicatorDto) {
        return (this.indicatorName == null ?
                    indicatorDto.getIndicatorName() == null :
                    indicatorDto.getIndicatorName().equals(this.indicatorName)) &&
                this.indicatorNum == indicatorDto.getIndicatorNum();
    }

    public TypeIndicator(TestCodeInfo testCode, int indicatorNum, String indicatorName) {
        this.testCode = testCode;
        this.indicatorNum = indicatorNum;
        this.indicatorName = indicatorName;
    }
}
