package com.typetest.personalities.repository;

import com.typetest.personalities.domain.IndicatorSetting;
import com.typetest.personalities.domain.TypeIndicator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndicatorSettingRepository extends JpaRepository<IndicatorSetting, Long> {
    @Query("select i.result from IndicatorSetting i" +
            " where i.typeIndicator = :indicator" +
            " and i.cuttingPoint < :point" +
            " order by i.cuttingPoint desc")
    List<String> findIndicatorResult(@Param("indicator") TypeIndicator indicator, @Param("point") int point, Pageable pageable);
}
