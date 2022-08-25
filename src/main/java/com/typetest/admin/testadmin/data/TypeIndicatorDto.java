package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeIndicator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TypeIndicatorDto {
    private Long id;
    private Integer indicatorNum;
    private String indicatorName;
    private List<IndicatorSettingDto> indicatorSettings = new ArrayList<>();

    public TypeIndicatorDto(TypeIndicator typeIndicator) {
        this.id = typeIndicator.getId();
        this.indicatorNum = typeIndicator.getIndicatorNum();
        this.indicatorName = typeIndicator.getIndicatorName();
        this.indicatorSettings = typeIndicator.getIndicatorSettings().stream()
                .map(IndicatorSettingDto::new).collect(Collectors.toList());
    }

    public boolean emptyValueCheck() {
        return (indicatorName == null || indicatorName.isEmpty()) &&
                indicatorNum == null;
    }
}
