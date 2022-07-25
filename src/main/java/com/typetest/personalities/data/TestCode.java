package com.typetest.personalities.data;

import lombok.Getter;

@Getter
public enum TestCode {
    EXAM("테스트검사", 0),
    MBTI("성격유형검사", 1),
    TCI("기질검사", 2);

    private final String krType;
    private final int number;

    TestCode(String krType, int number) {
        this.krType = krType;
        this.number = number;
    }
}
