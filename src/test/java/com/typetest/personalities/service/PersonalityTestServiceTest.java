package com.typetest.personalities.service;

import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.PersonalityTypeDetail;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.exam.repository.TestCode;
import com.typetest.personalities.repository.PersonalityTypeDetailRepository;
import com.typetest.personalities.repository.PersonalityTypeRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonalityTestServiceTest {

    @Autowired
    private LoginRepository loginRepository;

    private PersonalityTestService personalityTestService;

    @BeforeEach
    void beforeEach() {
        User user = new User("test_user", "test@test.com", "http://test.com/");
//        PersonalityType pt = new PersonalityType(user, TestCode.MBTI, "TEST");
//        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(user, TestCode.MBTI, 1);
//        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(user, TestCode.MBTI, 2);
//        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(user, TestCode.MBTI, 3);
//
        loginRepository.save(user);
//        personalityTypeRepository.save(pt);
//        personalityTypeDetailRepository.save(ptd1);
//        personalityTypeDetailRepository.save(ptd2);
//        personalityTypeDetailRepository.save(ptd3);

        personalityTestService = new PersonalityTestServiceImpl();
    }

    @Test
    public void calcTypeTest() throws Exception {
        //given
        User user = loginRepository.findByName("test_user").get();
        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Integer> answer = new HashMap<>();

        answer.put(1, 1);
        answer.put(2, 3);
        answer.put(3, 5);
        answer.put(4, 1);
        answer.put(5, 3);
        answer.put(6, 5);
        answer.put(7, 1);
        answer.put(8, 3);
        answer.put(9, 5);

        answerInfo.setUserId(user.getId());
        answerInfo.setTestCode(TestCode.EXAM);
        answerInfo.setAnswer(answer);

        //when
        String type = personalityTestService.calcType(answerInfo);

        //then
        System.out.println("type = " + type);
        Assertions.assertThat(type).hasSize(3);
    }
}