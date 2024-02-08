package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.IndicatorSetting;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndicatorSettingDto {
    private Long id;
    private String result;
    private Integer cuttingPoint;

    private Integer updated = 0;
    private Integer deleted = 0;

    public IndicatorSettingDto(IndicatorSetting indicatorSetting) {
        this.id = indicatorSetting.getId();
        this.result = indicatorSetting.getResult();
        this.cuttingPoint = indicatorSetting.getCuttingPoint();
    }

    public IndicatorSetting toEntity(TypeIndicator typeIndicator, TestCodeInfo testCodeInfo) {
        return IndicatorSetting.builder()
                .id(this.id)
                .testCode(testCodeInfo)
                .typeIndicator(typeIndicator)
                .cuttingPoint(this.cuttingPoint)
                .result(this.result)
                .build();
    }

    public boolean emptyValueCheck() {
        return (result == null || result.isEmpty()) &&
                cuttingPoint == null;
    }

    public boolean isDeletedEntity() {
        return id != null && this.deleted == 1;
    }

    public boolean isNewOrUpdatedEntity() {
        return id == null || this.updated == 1;
    }
}
