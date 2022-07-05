package com.typetest.test;

import com.typetest.personalities.exam.repository.TestType;
import org.junit.jupiter.api.Test;

public class ExamTest {

    @Test
    void enumTest() {
        TestType type = TestType.EXAM;
        System.out.println("type = " + type);
        System.out.println("type.getKrType() = " + type.getKrType());
        System.out.println(TestType.valueOf("EXAM").getKrType());
    }
}
