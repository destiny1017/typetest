package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeImageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TypeImage {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TYPE_INFO_ID")
    private TypeInfo typeInfo;

    private Integer imgNum;
    private String imageUrl;

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    @Builder
    public TypeImage(Long id, TypeInfo typeInfo, int imgNum, String imageUrl) {
        this.id = id;
        this.typeInfo = typeInfo;
        this.imgNum = imgNum;
        this.imageUrl = imageUrl;
    }

}
