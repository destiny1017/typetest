package com.typetest.personalities.repository;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.PersonalityTypeDetail;
import com.typetest.personalities.domain.TypeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @BeforeEach
    public void beforeEach() {
        TypeInfo typeInfo1 = new TypeInfo(AnswerType.BASIC, "BBA", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(AnswerType.BASIC, "AAA", "에에에이");
        TypeInfo typeInfo3 = new TypeInfo(AnswerType.BASIC, "BAB", "비에이비");
        TypeInfo typeInfo4 = new TypeInfo(AnswerType.CARD, "INTP", "인팁");
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(typeInfo4);

        em.flush();
    }

    @Test
    public void EntityInsertTest() throws Exception {
        //given
        User user = new User("test_user", "test@test.com", "http://test.com/", Role.USER, "디앙");
        PersonalityType pt = new PersonalityType(user, AnswerType.CARD, "TEST");
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(pt, user, AnswerType.CARD, 1, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(pt, user, AnswerType.CARD, 2, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(pt, user, AnswerType.CARD, 3, 3);

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
        User user = new User("test_user", "test@test.com", "http://test.com/", Role.USER, "디앙");
        PersonalityType pt = new PersonalityType(user, AnswerType.CARD, "TEST");
        PersonalityTypeDetail ptd1 = new PersonalityTypeDetail(pt, user, AnswerType.CARD, 1, 1);
        PersonalityTypeDetail ptd2 = new PersonalityTypeDetail(pt, user, AnswerType.CARD, 2, 2);
        PersonalityTypeDetail ptd3 = new PersonalityTypeDetail(pt, user, AnswerType.CARD, 3, 3);

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
    
    @Test
    @DisplayName("사용자 유형정보 가져오기 테스트")
    void getUserTypeListTest() {
        //given
        User user = new User("test1", "test1@test.com", "http://test.com/", Role.USER, "디앙");
        PersonalityType pt1 = new PersonalityType(user, AnswerType.BASIC, "AAA");
        PersonalityType pt2 = new PersonalityType(user, AnswerType.BASIC, "BBA");
        PersonalityType pt3 = new PersonalityType(user, AnswerType.CARD, "INTP");
        em.persist(user);
        em.persist(pt1);
        em.persist(pt2);
        em.persist(pt3);

        em.flush();

        //when
        User testUser = new User();
        testUser.setId(user.getId());
        List<TypeInfoData> userTypeList = personalityTypeRepository.getUserTypeList(testUser);

        //then
        assertThat(userTypeList).hasSize(3);

        assertThat(userTypeList.stream().map(u -> u.getAnswerType())).contains(AnswerType.BASIC);
        assertThat(userTypeList.stream().map(u -> u.getAnswerType())).contains(AnswerType.CARD);

        assertThat(userTypeList.stream().map(u -> u.getTypeCode())).contains("AAA");
        assertThat(userTypeList.stream().map(u -> u.getTypeCode())).contains("BBA");
        assertThat(userTypeList.stream().map(u -> u.getTypeCode())).contains("INTP");

    }

}