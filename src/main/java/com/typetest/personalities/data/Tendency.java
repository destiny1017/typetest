package com.typetest.personalities.data;

import lombok.Getter;

@Getter
public enum Tendency {
    H("Honesty-Humility", 1),
    E("Emotionality", 2),
    X("eXtraversion", 3),
    A("Agreeableness", 4),
    C("Conscientiousness", 5),
    O("Openness", 6);

    private String fullName;
    int num;

    Tendency(String fullName, int num) {
        this.fullName = fullName;
        this.num = num;
    }
}
