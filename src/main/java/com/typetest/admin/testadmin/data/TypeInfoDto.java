package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TypeInfoDto {
    private String typeCode;
    private String typeName;
    List<TypeImageDto> typeImageList = new ArrayList<>();
    List<TypeDescriptionDto> typeDescriptionList = new ArrayList<>();
    List<TypeRelationDto> typeRelationList = new ArrayList<>();

    public TypeInfoDto(TypeInfo typeInfo) {
        this.typeCode = typeInfo.getTypeCode();
        this.typeName = typeInfo.getTypeName();
        this.typeImageList = typeInfo.getImages().stream().map(TypeImageDto::new).collect(Collectors.toList());
        this.typeDescriptionList = typeInfo.getDescriptions().stream().map(TypeDescriptionDto::new).collect(Collectors.toList());;
        this.typeRelationList = typeInfo.getRelations().stream().map(TypeRelationDto::new).collect(Collectors.toList());;
    }
}
