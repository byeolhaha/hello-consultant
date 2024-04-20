package com.hellomeritz.member.repository.foreign;

import com.hellomeritz.member.domain.Foreigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ForeignJpaRepository extends JpaRepository<Foreigner, Long> {

    @Query("select case when exists(select 1 from Foreigner f where f.foreignerId = :userId and f.ipAddress = :ipAddress) then true else false end from Foreigner f")
    boolean isFc(@Param(value = "ipAddress") String ipAddress, @Param(value = "userId") Long userId);
}
