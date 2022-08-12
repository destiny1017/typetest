package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityQuestionRepository extends JpaRepository<PersonalityQuestion, Long> {
}
