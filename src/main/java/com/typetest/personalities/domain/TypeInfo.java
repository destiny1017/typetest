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

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
    private String typeCode;
    private String typeName;

    public TypeInfo(AnswerType answerType, String typeCode, String typeName) {
        this.answerType = answerType;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
