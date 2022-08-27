package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.IndicatorSetting;
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

    public boolean emptyValueCheck() {
        return (result == null || result.isEmpty()) &&
                cuttingPoint == null;
    }

    public boolean isNewEntity() {
        return id == null;
    }
}
