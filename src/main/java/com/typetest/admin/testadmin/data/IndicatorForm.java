package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeIndicator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IndicatorForm {
    String testCode;
    List<TypeIndicatorDto> indicatorList;
}
