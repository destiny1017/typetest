package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeDescription;
import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeDescriptionDto {
    private Long id;
    private Integer descNum;
    private String description;

    private Integer updated = 0;
    private Integer deleted = 0;

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

    public boolean isDeletedEntity() {
        return id != null && this.deleted == 1;
    }

    public boolean isNewOrUpdatedEntity() {
        return id == null || this.updated == 1;
    }
}
