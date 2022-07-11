package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityTypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityTypeDetailRepository extends JpaRepository<PersonalityTypeDetail, Long> {
}
