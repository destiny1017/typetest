package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TestCodeInfo;
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
    private Long id;
    private String typeCode;
    private String typeName;
    private boolean essentialType;
    List<TypeImageDto> typeImageList = new ArrayList<>();
    List<TypeDescriptionDto> typeDescriptionList = new ArrayList<>();
    TypeRelationDto typeRelation;

    private Integer updated = 0;
    private Integer deleted = 0;

    public TypeInfoDto(TypeInfo typeInfo) {
        this.id = typeInfo.getId();
        this.typeCode = typeInfo.getTypeCode();
        this.typeName = typeInfo.getTypeName();
        this.typeImageList = typeInfo.getImages().stream().map(TypeImageDto::new).collect(Collectors.toList());
        this.typeDescriptionList = typeInfo.getDescriptions().stream().map(TypeDescriptionDto::new).collect(Collectors.toList());;
        if(typeInfo.getTypeRelation() != null) {
            this.typeRelation = new TypeRelationDto(typeInfo.getTypeRelation());
        }
    }

    public TypeInfoDto(TypeInfo typeInfo, List<String> essentialCodeList) {
        this.id = typeInfo.getId();
        this.typeCode = typeInfo.getTypeCode();
        this.typeName = typeInfo.getTypeName();
        this.typeImageList = typeInfo.getImages().stream().map(TypeImageDto::new).collect(Collectors.toList());
        this.typeDescriptionList = typeInfo.getDescriptions().stream().map(TypeDescriptionDto::new).collect(Collectors.toList());;
        if(typeInfo.getTypeRelation() != null) {
            this.typeRelation = new TypeRelationDto(typeInfo.getTypeRelation());
        }
    }

    public TypeInfo toEntity(TestCodeInfo testCodeInfo) {
        return TypeInfo.builder()
                .id(this.id)
                .testCode(testCodeInfo)
                .typeCode(this.typeCode)
                .typeName(this.typeName)
                .build();
    }

    public boolean isDeletedEntity() {
        return id != null && this.deleted == 1;
    }

    public boolean isNewOrUpdatedEntity() {
        return id == null || this.updated == 1;
    }
}
