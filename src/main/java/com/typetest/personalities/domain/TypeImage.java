package com.typetest.personalities.domain;

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

    private int imgNum;
    private String imageUrl;

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public TypeImage(TypeInfo typeInfo, int imgNum, String imageUrl) {
        this.typeInfo = typeInfo;
        this.imgNum = imgNum;
        this.imageUrl = imageUrl;
    }
}
