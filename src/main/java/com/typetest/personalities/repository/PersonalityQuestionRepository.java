package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalityQuestionRepository extends JpaRepository<PersonalityQuestion, Long> {
    List<PersonalityQuestion> findByTestCode(TestCodeInfo testCode);
    Page<PersonalityQuestion> findByTestCode(TestCodeInfo testCodeInfo, Pageable pageable);
    Long countByTestCode(String testCode);
}
