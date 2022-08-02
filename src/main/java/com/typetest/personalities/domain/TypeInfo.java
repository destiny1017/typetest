package com.typetest.personalities.domain;

import com.typetest.personalities.data.TestCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

@Entity
@Getter
public class TypeInfo {

    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TestCode testCode;
    private String type;
    private TypeDescription description;
    private TypeImage image;
}
