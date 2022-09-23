package com.typetest.user.repository;

import com.typetest.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update USER_INFO u set u.nickname = :nickname where u.id = :id")
    void updateNickname(@Param("id") Long id, @Param("nickname") String nickname);
}
