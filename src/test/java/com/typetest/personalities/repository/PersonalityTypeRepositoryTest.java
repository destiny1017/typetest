package com.typetest.personalities.repository;

import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.PersonalityTypeDetail;
import com.typetest.personalities.exam.repository.TestCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonalityTypeRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PersonalityTypeDetailRepository personalityTypeDetailRepository;
    @Autowired
    private PersonalityTypeRepository personalityTypeRepository;

    @Test
    public void EntityInsertTest() throws Exception {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/");
        PersonalityType pt = new PersonalityType(user, TestCode.MBTI, "TEST");
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(pt, user, TestCode.MBTI, 1, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(pt, user, TestCode.MBTI, 2, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(pt, user, TestCode.MBTI, 3, 3);

        em.persist(user);
        em.persist(pt);
        em.persist(ptd1);
        em.persist(ptd2);
        em.persist(ptd3);

        //when
        User findUser = loginRepository.findById(user.getId()).get();
        PersonalityType findPt = personalityTypeRepository.findById(pt.getId()).get();
        List<PersonalityTypeDetail> byPersonalityType = personalityTypeDetailRepository.findByPersonalityType(pt);

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(byPersonalityType).contains(ptd1, ptd2, ptd3);

    }

    @Test
    public void savePersonal() throws Exception {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/");
        PersonalityType pt = new PersonalityType(user, TestCode.MBTI, "TEST");
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(pt, user, TestCode.MBTI, 1, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(pt, user, TestCode.MBTI, 2, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(pt, user, TestCode.MBTI, 3, 3);

        //when
        loginRepository.save(user);
        personalityTypeRepository.save(pt);
        personalityTypeDetailRepository.save(ptd1);
        personalityTypeDetailRepository.save(ptd2);
        personalityTypeDetailRepository.save(ptd3);

        User findUser = loginRepository.findById(user.getId()).get();
        PersonalityType findPt = personalityTypeRepository.findById(pt.getId()).get();
        List<PersonalityTypeDetail> byPersonalityType = personalityTypeDetailRepository.findByPersonalityType(pt);

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(byPersonalityType).contains(ptd1, ptd2, ptd3);
    }

}