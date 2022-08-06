package com.typetest.mypage.service;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.data.TestCode;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.TypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private EntityManager em;

    // InitDB사용중이므로 비활성화
//    @BeforeEach
//    void beforeEach() {
//        TypeInfo typeInfo1 = new TypeInfo(TestCode.EXAM, "BBA", "비비에이");
//        TypeInfo typeInfo2 = new TypeInfo(TestCode.EXAM, "AAA", "에에에이");
//        TypeInfo typeInfo3 = new TypeInfo(TestCode.EXAM, "BAB", "비에이비");
//        TypeInfo typeInfo4 = new TypeInfo(TestCode.MBTI, "INTP", "인팁");
//        TypeInfo typeInfo5 = new TypeInfo(TestCode.EXAM, "BBB", "비비비이");
//        em.persist(typeInfo1);
//        em.persist(typeInfo2);
//        em.persist(typeInfo3);
//        em.persist(typeInfo4);
//        em.persist(typeInfo5);
//
//        User user = new User("김대호",
//                "eogh6428@gmail.com",
//                "https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c",
//                Role.USER, "디앙");
//        PersonalityType pt1 = new PersonalityType(user, TestCode.EXAM, "AAA");
//        PersonalityType pt2 = new PersonalityType(user, TestCode.EXAM, "BBA");
//        PersonalityType pt3 = new PersonalityType(user, TestCode.MBTI, "INTP");
//        em.persist(user);
//        em.persist(pt1);
//        em.persist(pt2);
//        em.persist(pt3);
//
//        em.flush();
//    }

    @Test
    void getUserTypeInfoTest() {
        //given
        TypeInfo typeInfo1 = new TypeInfo(TestCode.EXAM, "AAB", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(TestCode.EXAM, "BAB", "비에이비");
        TypeInfo typeInfo3 = new TypeInfo(TestCode.MBTI, "ISFP", "잇픕");
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);

        User user = new User("김대호",
                "eogh6428@gmail.com",
                "https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c",
                Role.USER, "디앙");
        em.persist(user);

        PersonalityType pt1 = new PersonalityType(user, TestCode.EXAM, "BAB");
        PersonalityType pt2 = new PersonalityType(user, TestCode.MBTI, "ISFP");
        em.persist(pt1);
        em.persist(pt2);

        em.flush();

        //when
        Map<TestCode, TypeInfoData> typeMap = myPageService.getUserTypeInfo(user);

        //then
        assertThat(typeMap).hasSize(2);
        assertThat(typeMap.get(TestCode.EXAM).getTypeCode()).isEqualTo("BAB");
        assertThat(typeMap.get(TestCode.MBTI).getTypeCode()).isEqualTo("ISFP");
    }
}