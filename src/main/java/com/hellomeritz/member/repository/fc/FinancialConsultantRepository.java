package com.hellomeritz.member.repository.fc;

import com.hellomeritz.member.domain.FinancialConsultant;

import java.util.Optional;

public interface FinancialConsultantRepository {

    public FinancialConsultant getFinancialConsultant(long fcId);

}
