package com.typetest.personalities.repository;

import com.typetest.admin.testadmin.data.TypeRelationDto;
import com.typetest.personalities.domain.TypeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TypeRelationRepository extends JpaRepository<TypeRelation, Long> {
    @Query(value = "insert into type_relation (type_info_id, best_type, worst_type)" +
            " values (:#{#dto.typeInfoId}, :#{#dto.bestTypeId}, :#{#dto.worstTypeId})", nativeQuery = true)
    void insertTypeRelation(@Param("dto") TypeRelationDto typeRelationDto);

    @Query(value = "update type_relation t" +
            " set t.type_info_id=:#{#dto.typeInfoId}, t.best_type=:#{#dto.bestTypeId}, t.worst_type=:#{#dto.worstTypeId}" +
            " where t.id = :#{#dto.id}", nativeQuery = true)
    void updateTypeRelation(@Param("dto") TypeRelationDto typeRelationDto);
}
