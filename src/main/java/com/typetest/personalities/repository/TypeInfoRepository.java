package com.typetest.personalities.repository;

import com.typetest.personalities.domain.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeInfoRepository extends JpaRepository<TypeInfo, Long> {
    Optional<TypeInfo> findByTypeCode(String type);
}
