package com.typetest.personalities.repository;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestResult;
import com.typetest.personalities.domain.TestResultDetail;
import com.typetest.personalities.domain.TestCodeInfo;
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
class TestResultRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private TestResultDetailRepository testResultDetailRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private TestCodeInfoRepository testCodeInfoRepository;

    @BeforeEach
    public void beforeEach() {
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "BAB", "비에이비");
        TypeInfo typeInfo4 = new TypeInfo(testCodeInfo2, "INTP", "인팁");
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(typeInfo4);

        em.flush();
    }

    @Test
    public void EntityInsertTest() throws Exception {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo = new TypeInfo(testCodeInfo1, "TEST", "테스트");
        TestResult pt = new TestResult(user, testCodeInfo1, typeInfo);
        TestResultDetail ptd1 = new TestResultDetail(pt, user, testCodeInfo1, 1, 1);
        TestResultDetail ptd2 = new TestResultDetail(pt, user, testCodeInfo1, 2, 2);
        TestResultDetail ptd3 = new TestResultDetail(pt, user, testCodeInfo1, 3, 3);

        em.persist(user);
        em.persist(pt);
        em.persist(typeInfo);
        em.persist(ptd1);
        em.persist(ptd2);
        em.persist(ptd3);

        //when
        User findUser = loginRepository.findById(user.getId()).get();
        TestResult findPt = testResultRepository.findById(pt.getId()).get();
        List<TestResultDetail> byTestResult = testResultDetailRepository.findByTestResult(pt);

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(byTestResult).contains(ptd1, ptd2, ptd3);

    }

    @Test
    public void savePersonal() throws Exception {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo = new TypeInfo(testCodeInfo1, "TEST", "테스트");
        TestResult pt = new TestResult(user, testCodeInfo1, typeInfo);
        TestResultDetail ptd1 = new TestResultDetail(pt, user, testCodeInfo1, 1, 1);
        TestResultDetail ptd2 = new TestResultDetail(pt, user, testCodeInfo1, 2, 2);
        TestResultDetail ptd3 = new TestResultDetail(pt, user, testCodeInfo1, 3, 3);

        //when
        em.persist(typeInfo);
        loginRepository.save(user);
        testCodeInfoRepository.save(testCodeInfo1);
        testResultRepository.save(pt);
        testResultDetailRepository.save(ptd1);
        testResultDetailRepository.save(ptd2);
        testResultDetailRepository.save(ptd3);

        User findUser = loginRepository.findById(user.getId()).get();
        TestResult findPt = testResultRepository.findById(pt.getId()).get();
        List<TestResultDetail> byTestResult = testResultDetailRepository.findByTestResult(pt);

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findPt.getId()).isEqualTo(pt.getId());
        assertThat(byTestResult).contains(ptd1, ptd2, ptd3);
    }
    
    @Test
    @DisplayName("사용자 유형정보 가져오기 테스트")
    void getUserTypeListTest() {
        //given
        User user = User.builder()
                .name("test_user")
                .email("test@test.com")
                .picture("http://test.com/")
                .role(Role.USER)
                .nickname("디앙")
                .build();
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "AAA", "에에에이");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "BBA", "비비에이");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo2, "INTP", "인팁");
        TestResult pt1 = new TestResult(user, testCodeInfo1, typeInfo1);
        TestResult pt2 = new TestResult(user, testCodeInfo1, typeInfo2);
        TestResult pt3 = new TestResult(user, testCodeInfo2, typeInfo3);
        em.persist(user);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);
        em.persist(pt1);
        em.persist(pt2);
        em.persist(pt3);

        em.flush();

        //when
        User testUser = User.builder().id(user.getId()).build();
        List<TypeInfoData> userTypeList = testResultRepository.getUserTypeList(testUser);

        //then
        assertThat(userTypeList).hasSize(3);

        assertThat(userTypeList.stream().map(u -> u.getTestCode())).contains("EXAMTEST");
        assertThat(userTypeList.stream().map(u -> u.getTestCode())).contains("CARDTEST");

        assertThat(userTypeList.stream().map(u -> u.getTypeInfo())).contains(typeInfo1);
        assertThat(userTypeList.stream().map(u -> u.getTypeInfo())).contains(typeInfo2);
        assertThat(userTypeList.stream().map(u -> u.getTypeInfo())).contains(typeInfo3);

    }

}