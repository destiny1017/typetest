package com.typetest.personalities.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointWrapper {
    private int point;

    public void addPoint(int point) {
        this.point += point;
    }
}
