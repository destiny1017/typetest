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
    private TypeInfo typeInfo;
    private TypeInfo bestType;
    private TypeInfo worstType;

    public TypeRelationDto(TypeRelation typeRelation) {
        this.typeInfo = typeRelation.getTypeInfo();
        this.bestType = typeRelation.getBestType();
        this.worstType = typeRelation.getWorstType();
    }
}
