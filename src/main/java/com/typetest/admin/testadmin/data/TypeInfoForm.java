package com.typetest.admin.testadmin.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TypeInfoForm {
    @NotEmpty
    private String typeInfoTestCode;
    @NotNull
    private List<TypeInfoDto> typeInfoList = new ArrayList<>();
}
