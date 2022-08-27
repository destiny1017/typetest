package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeDescription;
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

    public boolean isNewEntity() {
        return id == null;
    }
}
