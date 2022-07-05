package com.typetest.login.repository;

import com.typetest.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 이미 email을 통해 생성된 사용자인지 체크
}
