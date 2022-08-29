package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeDescriptionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TypeDescription {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TYPE_INFO_ID")
    private TypeInfo typeInfo;

    private Integer descNum;
    private String description;

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public TypeDescription(TypeInfo typeInfo, int descNum, String description) {
        this.typeInfo = typeInfo;
        this.descNum = descNum;
        this.description = description;
    }

    public TypeDescription(TypeInfo typeInfo, TypeDescriptionDto typeDescriptionDto) {
        this.id = typeDescriptionDto.getId();
        this.typeInfo = typeInfo;
        this.descNum = typeDescriptionDto.getDescNum();
        this.description = typeDescriptionDto.getDescription();
    }
}
