package com.typetest.personalities.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExamResultInfo {

    private String type;
    private String description;
    private String imageUrl;

    public ExamResultInfo() {
    }

    public ExamResultInfo(String type, String typeDescription, String imageUrl) {
        this.type = type;
        this.description = typeDescription;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ExamResultInfo{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
