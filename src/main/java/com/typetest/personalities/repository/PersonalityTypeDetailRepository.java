package com.typetest.personalities.repository;

import com.typetest.login.domain.User;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.PersonalityTypeDetail;
import com.typetest.personalities.exam.repository.TestCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalityTypeDetailRepository extends JpaRepository<PersonalityTypeDetail, Long> {
    List<PersonalityTypeDetail> findByUserAndTestCode(User user, TestCode testCode);
    List<PersonalityTypeDetail> findByPersonalityType(PersonalityType pt);
}
