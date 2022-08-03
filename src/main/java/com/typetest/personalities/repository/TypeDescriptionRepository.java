package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.TypeDescription;
import com.typetest.personalities.domain.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeDescriptionRepository extends JpaRepository<TypeDescription, Long> {
    List<TypeDescription> findByTypeInfo(TypeInfo typeInfo);
}
