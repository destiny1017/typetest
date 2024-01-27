package com.typetest.admin.testadmin.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IndicatorForm {
    @NotEmpty
    String indicatorTestCode;
    @NotNull
    List<TypeIndicatorDto> indicatorList;

    @Builder
    public IndicatorForm(String indicatorTestCode, List<TypeIndicatorDto> indicatorList) {
        this.indicatorTestCode = indicatorTestCode;
        this.indicatorList = indicatorList;
    }
}
