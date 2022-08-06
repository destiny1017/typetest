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
    @JoinColumn
    private TypeInfo typeInfo;

    private String imageUrl;

    public TypeImage(TypeInfo typeInfo, String imageUrl) {
        this.typeInfo = typeInfo;
        this.imageUrl = imageUrl;
    }
}
