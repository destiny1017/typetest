package com.typetest.personalities.repository;

import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TypeInfoRepository extends JpaRepository<TypeInfo, Long> {
    Optional<TypeInfo> findByTestCodeAndTypeCode(TestCodeInfo testCode, String type);
    List<TypeInfo> findByTestCode(TestCodeInfo testCodeInfo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TypeInfo t set t.resultCount = t.resultCount + 1 where t = :typeInfo")
    void plusResultCount(@Param("typeInfo") TypeInfo typeInfo);
}
