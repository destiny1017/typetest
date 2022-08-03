package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.TypeImage;
import com.typetest.personalities.domain.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeImageRepository extends JpaRepository<TypeImage, Long> {
    List<TypeImage> findByTypeInfo(TypeInfo typeInfo);
}
