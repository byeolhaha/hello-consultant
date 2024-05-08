package com.hellomeritz.member.repository.fc;

import com.hellomeritz.member.domain.FinancialConsultant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialConsultantJapRepository extends JpaRepository<FinancialConsultant, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select fc from FinancialConsultant as fc where fc.consultationState = 'AVAILABLE'")
    List<FinancialConsultant> findFinancialConsultant();

}
