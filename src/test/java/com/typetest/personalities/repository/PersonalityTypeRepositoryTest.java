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
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(user, TestCode.MBTI, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(user, TestCode.MBTI, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(user, TestCode.MBTI, 3);

        em.persist(user);
        em.persist(pt);
        em.persist(ptd1);
        em.persist(ptd2);
        em.persist(ptd3);

        em.flush();
        em.clear();

        //when
        User findUser = loginRepository.findById(user.getId()).get();
        PersonalityType findPt = personalityTypeRepository.findById(pt.getId()).get();
        PersonalityTypeDetail findPtd1 = personalityTypeDetailRepository.findById(ptd1.getId()).get();
        PersonalityTypeDetail findPtd2 = personalityTypeDetailRepository.findById(ptd2.getId()).get();
        PersonalityTypeDetail findPtd3 = personalityTypeDetailRepository.findById(ptd3.getId()).get();

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(findPtd1.getId()).isEqualTo(ptd1.getId());
        assertThat(findPtd2.getId()).isEqualTo(ptd2.getId());
        assertThat(findPtd3.getId()).isEqualTo(ptd3.getId());

    }

    @Test
    public void detailRepositoryTest() throws Exception {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/");
        PersonalityType pt = new PersonalityType(user, TestCode.MBTI, "TEST");
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(user, TestCode.MBTI, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(user, TestCode.MBTI, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(user, TestCode.MBTI, 3);

        em.persist(user);
        em.persist(pt);
        em.persist(ptd1);
        em.persist(ptd2);
        em.persist(ptd3);

//        em.flush();
//        em.clear();

        //when
        List<PersonalityTypeDetail> findDetail = personalityTypeDetailRepository.findByUserAndTestCode(user, TestCode.MBTI);

        //then
        assertThat(findDetail).containsExactly(ptd1, ptd2, ptd3);
    }

    @Test
    public void savePersonal() throws Exception {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/");
        PersonalityType pt = new PersonalityType(user, TestCode.MBTI, "TEST");
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(user, TestCode.MBTI, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(user, TestCode.MBTI, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(user, TestCode.MBTI, 3);

        //when
        loginRepository.save(user);
        personalityTypeRepository.save(pt);
        personalityTypeDetailRepository.save(ptd1);
        personalityTypeDetailRepository.save(ptd2);
        personalityTypeDetailRepository.save(ptd3);

        User findUser = loginRepository.findById(user.getId()).get();
        PersonalityType findPt = personalityTypeRepository.findById(pt.getId()).get();
        PersonalityTypeDetail findPtd1 = personalityTypeDetailRepository.findById(ptd1.getId()).get();
        PersonalityTypeDetail findPtd2 = personalityTypeDetailRepository.findById(ptd2.getId()).get();
        PersonalityTypeDetail findPtd3 = personalityTypeDetailRepository.findById(ptd3.getId()).get();

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(findPtd1.getId()).isEqualTo(ptd1.getId());
        assertThat(findPtd2.getId()).isEqualTo(ptd2.getId());
        assertThat(findPtd3.getId()).isEqualTo(ptd3.getId());
    }
}