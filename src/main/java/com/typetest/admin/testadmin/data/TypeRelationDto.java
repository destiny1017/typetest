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

    public TypeRelationDto(TypeRelation typeRelation) {
        this.id = typeRelation.getId();
        this.typeInfoId = typeRelation.getTypeInfo().getId();
        this.bestTypeId = typeRelation.getBestType().getId();
        this.worstTypeId = typeRelation.getWorstType().getId();
    }
}
