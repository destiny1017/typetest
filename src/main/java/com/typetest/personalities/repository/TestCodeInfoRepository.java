package com.typetest.personalities.repository;

import com.typetest.personalities.domain.TestCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCodeInfoRepository extends JpaRepository<TestCodeInfo, String> {
}
