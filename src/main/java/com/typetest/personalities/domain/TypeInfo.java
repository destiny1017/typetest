package com.typetest.personalities.domain;

import com.typetest.personalities.data.TestCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TypeInfo {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TestCode testCode;
    private String typeCode;
    private String typeName;

    public TypeInfo(TestCode testCode, String typeCode, String typeName) {
        this.testCode = testCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
