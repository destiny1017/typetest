package com.typetest.personalities.repository;

import com.typetest.personalities.domain.TestCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TestCodeInfoRepository extends JpaRepository<TestCodeInfo, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TestCodeInfo t set t.playCount = t.playCount + 1 where t = :testCodeInfo")
    void plusPlayCount(@Param("testCodeInfo") TestCodeInfo testCodeInfo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TestCodeInfo t set t.active = 0 where t = :testCodeInfo")
    void disableTest(@Param("testCodeInfo") TestCodeInfo testCodeInfo);
}
