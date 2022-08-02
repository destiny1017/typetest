package com.typetest.personalities.domain;

import com.typetest.personalities.data.TestCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class TypeImage {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private TypeInfo typeInfo;

    private String imageUrl;
}
