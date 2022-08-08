package com.typetest.personalities.data;

import lombok.Getter;

@Getter
public enum AnswerType {
    EXAM("점수선택형", 0),
    CARD("답변선택형", 1),
    INTER("인터렉티브", 2);

    private final String krType;
    private final int number;

    AnswerType(String krType, int number) {
        this.krType = krType;
        this.number = number;
    }
}
