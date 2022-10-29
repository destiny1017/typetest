package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeIndicator;
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
}
