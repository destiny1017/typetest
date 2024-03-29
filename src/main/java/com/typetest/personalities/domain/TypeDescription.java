package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeDescriptionDto;
import lombok.Builder;
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

    @Builder
    public TypeDescription(Long id, TypeInfo typeInfo, int descNum, String description) {
        this.id = id;
        this.typeInfo = typeInfo;
        this.descNum = descNum;
        this.description = description;
    }

}
