package com.typetest.personalities.repository;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TestCodeInfoRepositoryTest {

    @Autowired
    private TestCodeInfoRepository testCodeInfoRepository;

    @Test
    @DisplayName("테스트 playCount 증가 테스트")
    void plusPlayCount() {
        //given
        TestCodeInfo testCode = new TestCodeInfo("COUNT_TEST", "테스트", AnswerType.EXAM);

        //when
        testCodeInfoRepository.save(testCode);
        testCodeInfoRepository.flush();

        Assertions.assertEquals(0, testCode.getPlayCount());
        testCodeInfoRepository.plusPlayCount(testCode);
        testCode = testCodeInfoRepository.findById(testCode.getTestCode()).get();

        //then
        Assertions.assertEquals(1, testCode.getPlayCount());

    }

}