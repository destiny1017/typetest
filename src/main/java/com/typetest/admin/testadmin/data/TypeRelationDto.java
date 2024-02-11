package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeInfo;
import com.typetest.personalities.domain.TypeRelation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeRelationDto {
    private Long id;
    private Long typeInfoId;
    private Long bestTypeId;
    private Long worstTypeId;

    private Integer updated = 0;
    private Integer deleted = 0;

    public TypeRelationDto(TypeRelation typeRelation) {
        this.id = typeRelation.getId();
        this.typeInfoId = typeRelation.getTypeInfo().getId();
        this.bestTypeId = typeRelation.getBestType().getId();
        this.worstTypeId = typeRelation.getWorstType().getId();
    }

    public TypeRelation toEntity() {
        return TypeRelation.builder()
                .id(this.id)
                .typeInfo(TypeInfo.builder().id(this.typeInfoId).build())
                .bestType(TypeInfo.builder().id(this.bestTypeId).build())
                .worstType(TypeInfo.builder().id(this.worstTypeId).build())
                .build();
    }

    public boolean isNewEntity() {
        return id == null;
    }
}
