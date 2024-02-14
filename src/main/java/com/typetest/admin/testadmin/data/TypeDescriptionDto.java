package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeDescription;
import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeDescriptionDto extends EntityState {
    private Integer descNum;
    private String description;

    public TypeDescriptionDto(TypeDescription typeDescription) {
        this.id = typeDescription.getId();
        this.descNum = typeDescription.getDescNum();
        this.description = typeDescription.getDescription();
    }

    public TypeDescription toEntity(TypeInfo typeInfo) {
        return TypeDescription.builder()
                .id(this.id)
                .typeInfo(typeInfo)
                .descNum(this.descNum)
                .description(this.description)
                .build();
    }

}
