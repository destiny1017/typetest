package com.typetest.test;

import com.typetest.personalities.data.TestCode;
import org.junit.jupiter.api.Test;

public class ExamTest {

    @Test
    void enumTest() {
        TestCode type = TestCode.EXAM;
        System.out.println("type = " + type);
        System.out.println("type.getKrType() = " + type.getKrType());
        System.out.println(TestCode.valueOf("EXAM").getKrType());
    }
}
