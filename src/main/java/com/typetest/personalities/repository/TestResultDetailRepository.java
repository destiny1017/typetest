package com.typetest.personalities.repository;

import com.typetest.user.domain.User;
import com.typetest.personalities.domain.TestResult;
import com.typetest.personalities.domain.TestResultDetail;
import com.typetest.personalities.domain.TestCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultDetailRepository extends JpaRepository<TestResultDetail, Long> {
    List<TestResultDetail> findByUserAndTestCode(User user, TestCodeInfo testCode);
    List<TestResultDetail> findByTestResult(TestResult pt);
}
