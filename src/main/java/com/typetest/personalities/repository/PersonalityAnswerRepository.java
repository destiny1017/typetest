package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityAnswerRepository extends JpaRepository<PersonalityAnswer, Long> {
}
