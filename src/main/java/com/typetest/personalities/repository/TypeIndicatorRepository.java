package com.typetest.personalities.repository;

import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeIndicatorRepository extends JpaRepository<TypeIndicator, Long> {
    List<TypeIndicator> findByTestCode(TestCodeInfo testCodeInfo);
}
