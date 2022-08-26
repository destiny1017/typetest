package com.typetest.admin.testadmin.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TypeInfoForm {
    private String typeInfoTestCode;
    private List<TypeInfoDto> typeInfoList = new ArrayList<>();
}
