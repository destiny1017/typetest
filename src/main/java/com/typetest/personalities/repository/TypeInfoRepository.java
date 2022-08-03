package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeInfoRepository extends JpaRepository<TypeInfo, Long> {
}
