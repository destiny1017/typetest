package com.typetest.personalities.repository;

import com.typetest.user.domain.User;
import com.typetest.personalities.domain.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends
        JpaRepository<TestResult, Long>,
        TestResultRepositoryCustom
{
    List<TestResult> findByUser(User user);
    List<TestResult> findByUserId(Long userId);
}
