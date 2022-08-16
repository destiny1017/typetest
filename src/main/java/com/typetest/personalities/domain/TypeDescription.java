package com.typetest.personalities.domain;

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
    @JoinColumn
    private TypeInfo typeInfo;

    private int discNum;
    private String description;

    public TypeDescription(TypeInfo typeInfo, int discNum, String description) {
        this.typeInfo = typeInfo;
        this.discNum = discNum;
        this.description = description;
    }
}
