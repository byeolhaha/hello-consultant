package com.hellomeritz.member.repository;

import com.hellomeritz.member.domain.Foreigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ForeignJpaRepository extends JpaRepository<Foreigner, Long> {

    @Query("select coalesce(f.id, 0) from Foreigner f where f.macAddress = :macAddress")
    Long getUserId(@Param(value = "macAddress") String macAddress);
}
