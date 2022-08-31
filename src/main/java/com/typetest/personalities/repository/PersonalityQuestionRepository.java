package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.data.ExamQuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonalityQuestionRepository extends JpaRepository<PersonalityQuestion, Long> {
    List<PersonalityQuestion> findByTestCode(TestCodeInfo testCode);
    List<PersonalityQuestion> findByTestCodeOrderByNum(TestCodeInfo testCode);

    @Query("select new com.typetest.personalities.data.ExamQuestionDto(pq) " +
            "from PersonalityQuestion pq " +
            "where pq.testCode = :testCode")
    Page<ExamQuestionDto> findByTestCode(@Param("testCode") TestCodeInfo testCodeInfo, Pageable pageable);
    Long countByTestCode(TestCodeInfo testCode);
}
