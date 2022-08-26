package com.typetest.personalities.repository;

import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeInfoRepository extends JpaRepository<TypeInfo, Long> {
    Optional<TypeInfo> findByTestCodeAndTypeCode(TestCodeInfo testCode, String type);
    List<TypeInfo> findByTestCode(TestCodeInfo testCodeInfo);
}
