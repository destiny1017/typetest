package com.typetest.mypage.service;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestResult;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("사용자의 저장된 테스트 결과 정보 가져오기")
    void getUserTypeInfoTest() throws InterruptedException {
        // 임시 테스트코드
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        //given
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "AAB", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo2, "ISFP", "잇픕");
        Thread.sleep(1000);
        TypeInfo typeInfo4 = new TypeInfo(testCodeInfo1, "BBB", "비비비");
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);

        User user = User.builder()
                .name("김대호")
                .email("eogh6428@gmail.com")
                .picture("https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c")
                .role(Role.USER)
                .nickname("디앙")
                .build();

        em.persist(user);

        TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo2);
        TestResult pt2 = new TestResult(user, testCodeInfo2, typeInfo3);
        em.persist(pt1);
        em.persist(pt2);

        em.flush();

        //when
        Map<String, TypeInfoData> typeMap = myPageService.getUserTypeInfo(user);

        //then
        assertThat(typeMap).hasSize(2);
        assertThat(typeMap.get(testCodeInfo1.getTestCode()).getTypeInfo().getTestCode()).isEqualTo(typeInfo4.getTestCode());
        assertThat(typeMap.get(testCodeInfo2.getTestCode()).getTypeInfo().getTestCode()).isEqualTo(typeInfo3.getTestCode());
    }
}