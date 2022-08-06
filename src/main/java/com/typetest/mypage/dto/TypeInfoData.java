package com.typetest.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.typetest.personalities.data.AnswerType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TypeInfoData {
    private AnswerType answerType;
    private String typeCode;
    private String typeName;
    private LocalDateTime createDate;

    @QueryProjection
    public TypeInfoData(AnswerType answerType, String typeCode, LocalDateTime createDate, String typeName) {
        this.answerType = answerType;
        this.typeCode = typeCode;
        this.createDate = createDate;
        this.typeName = typeName;
    }
}
