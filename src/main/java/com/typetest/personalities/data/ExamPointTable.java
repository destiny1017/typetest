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

        allocator.add(a); // 1
        allocator.add(b); // 2
        allocator.add(c); // 3
        allocator.add(a); // 4
        allocator.add(b); // 5
        allocator.add(c); // 6
        allocator.add(a); // 7
        allocator.add(b); // 8
        allocator.add(c); // 9

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
        if(a.getPoint() > 27) type += "A";
        else type += "B";

        // b 지표 결정
        if(b.getPoint() > 27) type += "A";
        else type += "B";

        // c 지표 결정
        if(c.getPoint() > 27) type += "A";
        else type += "B";

        return type;
    }

}
