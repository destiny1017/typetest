package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityAnswer;
import com.typetest.personalities.domain.TestCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalityAnswerRepository extends JpaRepository<PersonalityAnswer, Long> {
    List<PersonalityAnswer> findByTestCode(TestCodeInfo testCode);
}
