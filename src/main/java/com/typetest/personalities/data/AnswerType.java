package com.typetest.personalities.data;

import lombok.Getter;

@Getter
public enum AnswerType {
    EXAM("점수선택형", "/assets/images/contents/EXAM_img.png", 0),
    CARD("답변선택형", "/assets/images/contents/CARD_img.png", 1),
    INTER("인터랙티브", "/assets/images/contents/INTER_img.png", 2);

    private final String krType;
    private final String image;
    private final int number;

    AnswerType(String krType, String image, int number) {
        this.krType = krType;
        this.image = image;
        this.number = number;
    }
}
