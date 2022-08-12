package com.typetest.personalities.data;

public enum Tendency {
    H("Honesty-Humility"),
    E("Emotionality"),
    X("eXtraversion"),
    A("Agreeableness"),
    C("Conscientiousness"),
    O("Openness");

    private String fullName;

    Tendency(String fullName) {
        this.fullName = fullName;
    }
}
