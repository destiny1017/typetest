package com.typetest.personalities.repository;

import com.typetest.personalities.domain.IndicatorSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicatorSettingRepository extends JpaRepository<IndicatorSetting, Long> {
}
