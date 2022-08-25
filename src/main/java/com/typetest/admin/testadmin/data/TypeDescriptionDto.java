package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeDescriptionDto {
    private Integer descNum;
    private String description;

    public TypeDescriptionDto(TypeDescription typeDescription) {
        this.descNum = typeDescription.getDescNum();
        this.description = typeDescription.getDescription();
    }
}
