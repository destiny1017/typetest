package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeImageDto;
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

    public TypeImage(TypeInfo typeInfo, int imgNum, String imageUrl) {
        this.typeInfo = typeInfo;
        this.imgNum = imgNum;
        this.imageUrl = imageUrl;
    }

    public TypeImage(TypeInfo typeInfo, TypeImageDto typeImageDto) {
        this.id = typeImageDto.getId();
        this.typeInfo = typeInfo;
        this.imgNum = typeImageDto.getImgNum();
        this.imageUrl = typeImageDto.getImageUrl();
    }
}
