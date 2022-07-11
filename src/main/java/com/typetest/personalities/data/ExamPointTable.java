package com.typetest.personalities.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExamPointTable {

    private PointWrapper a;
    private PointWrapper b;
    private PointWrapper c;

    private List<PointWrapper> allocator;

    public ExamPointTable() {
        a = new PointWrapper();
        b = new PointWrapper();
        c = new PointWrapper();

        allocator = new ArrayList<>();
        allocator.add(new PointWrapper()); // 0 index 처리용
        allocator.add(a); // 1
        allocator.add(b); // 2
        allocator.add(c); // 3
        allocator.add(a); // 4
        allocator.add(b); // 5
        allocator.add(c); // 6
        allocator.add(a); // 7
        allocator.add(b); // 8
        allocator.add(c); // 9
    }

    public String getType() {
        String type = "";

        // a 지표 결정
        if(a.getPoint() > 11) type += "A";
        else if(a.getPoint() > 7) type += "B";
        else type += "C";

        // b 지표 결정
        if(b.getPoint() > 11) type += "A";
        else if(b.getPoint() > 7) type += "B";
        else type += "C";

        // c 지표 결정
        if(c.getPoint() > 11) type += "A";
        else if(c.getPoint() > 7) type += "B";
        else type += "C";

        return type;
    }

}
