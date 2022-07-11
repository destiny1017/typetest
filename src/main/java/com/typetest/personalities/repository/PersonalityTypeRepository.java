package com.typetest.personalities.repository;

import com.typetest.login.domain.User;
import com.typetest.personalities.domain.PersonalityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityTypeRepository extends JpaRepository<PersonalityType, Long> {
    PersonalityType findByUser(User user);
}
