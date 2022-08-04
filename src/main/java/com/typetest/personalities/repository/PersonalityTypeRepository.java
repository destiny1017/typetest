package com.typetest.personalities.repository;

import com.typetest.login.domain.User;
import com.typetest.personalities.domain.PersonalityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalityTypeRepository extends
        JpaRepository<PersonalityType, Long>,
        PersonalityTypeRepositoryCustom
{
    List<PersonalityType> findByUser(User user);
    List<PersonalityType> findByUserId(Long userId);
}
