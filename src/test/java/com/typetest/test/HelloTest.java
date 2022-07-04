package com.typetest.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloTest {

    @Test
    public void helloTest() throws Exception {
        //given
        Hello hello = new Hello();
        hello.setName("testHello1");

        //then

    }
}