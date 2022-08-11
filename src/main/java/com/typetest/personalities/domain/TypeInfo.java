package com.typetest.personalities.domain;

import com.typetest.personalities.data.AnswerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TypeInfo {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private String typeCode;
    private String typeName;

    public TypeInfo(TestCodeInfo testCode, String typeCode, String typeName) {
        this.testCode = testCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
