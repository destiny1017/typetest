package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityTypeRepository extends JpaRepository<PersonalityType, Long> {
}
