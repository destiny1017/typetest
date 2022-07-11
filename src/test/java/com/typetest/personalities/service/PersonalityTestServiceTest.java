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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonalityTestServiceTest {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    @Qualifier("personalityTestServiceImpl")
    private PersonalityTestService personalityTestService;

    @Autowired
    private PersonalityTypeDetailRepository ptdRepository;

    @Autowired
    private PersonalityTypeRepository ptRepository;

    @BeforeEach
    void beforeEach() {
        User user = new User("test_user", "test@test.com", "http://test.com/");
        loginRepository.save(user);
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

    @Test
    void saveInfoTest() {
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
        personalityTestService.saveTestInfo(answerInfo, "CBA");

        //then
        PersonalityType byUser = ptRepository.findByUser(user);
        List<PersonalityTypeDetail> byUserAndTestCode = ptdRepository.findByUserAndTestCode(user, TestCode.EXAM);

        byUser.getUser();
        System.out.println("byUs = " + byUser);
        for (PersonalityTypeDetail ptd : byUserAndTestCode) {
            ptd.getUser();
            System.out.println("ptd = " + ptd);
        }
    }
}