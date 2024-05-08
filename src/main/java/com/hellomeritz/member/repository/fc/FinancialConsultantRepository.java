package com.hellomeritz.member.repository.fc;

import com.hellomeritz.member.domain.FinancialConsultant;

import java.util.List;
import java.util.Optional;


public interface FinancialConsultantRepository {

    FinancialConsultant getFinancialConsultant(long fcId);

    FinancialConsultant save(FinancialConsultant financialConsultant);

    List<FinancialConsultant> getFinancialConsultantWithAvailable();

}
