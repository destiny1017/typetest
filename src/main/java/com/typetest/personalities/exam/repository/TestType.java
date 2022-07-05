package com.typetest.personalities.exam.repository;

import lombok.Getter;

@Getter
public enum TestType {
    EXAM("테스트검사", 0),
    MBTI("성격유형검사", 1),
    TCI("기질검사", 2);

    private final String krType;
    private final int number;

    TestType(String krType, int number) {
        this.krType = krType;
        this.number = number;
    }
}
