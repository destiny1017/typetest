package com.typetest.personalities.service;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.PersonalityTypeDetail;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.data.TestCode;
import com.typetest.personalities.repository.PersonalityTypeDetailRepository;
import com.typetest.personalities.repository.PersonalityTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class PersonalityTestServiceTest {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
//    @Qualifier("personalityTestServiceImpl")
    private PersonalityTestService personalityTestService;

    @Autowired
    private PersonalityTypeDetailRepository ptdRepository;

    @Autowired
    private PersonalityTypeRepository ptRepository;

    @Test
    public void calcTypeTest() throws Exception {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/", Role.USER, "디앙");
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
        Assertions.assertThat(type).hasSize(3);
    }

    @Test
    void saveInfoTest() {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/", Role.USER, "디앙");
        loginRepository.save(user);
        PersonalitiesAnswerInfo answerInfo = new PersonalitiesAnswerInfo();
        HashMap<Integer, Integer> answer = new HashMap<>();

        answer.put(1, 1);
        answer.put(2, 1);
        answer.put(3, 1);
        answer.put(4, 1);
        answer.put(5, 1);
        answer.put(6, 1);
        answer.put(7, 1);
        answer.put(8, 1);
        answer.put(9, 1);

        answerInfo.setUserId(user.getId());
        answerInfo.setTestCode(TestCode.EXAM);
        answerInfo.setAnswer(answer);

        //when
        personalityTestService.saveTestInfo(answerInfo, "BBB");

        //then
        PersonalityType byUser = ptRepository.findByUser(user).get(0);
        List<PersonalityTypeDetail> byUserAndTestCode = ptdRepository.findByUserAndTestCode(user, TestCode.EXAM);

        byUser.getUser();
        System.out.println("byUs = " + byUser);
        for (PersonalityTypeDetail ptd : byUserAndTestCode) {
            ptd.getUser();
            System.out.println("ptd = " + ptd);
        }
    }
}