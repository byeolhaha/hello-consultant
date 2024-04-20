package com.hellomeritz.member.repository.fc;

import com.hellomeritz.member.domain.FinancialConsultant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FinancialConsultantRepositoryImpl implements FinancialConsultantRepository{

    private final FinancialConsultantJapRepository financialConsultantJapRepository;

    public FinancialConsultantRepositoryImpl(FinancialConsultantJapRepository financialConsultantJapRepository) {
        this.financialConsultantJapRepository = financialConsultantJapRepository;
    }

    @Override
    public FinancialConsultant getFinancialConsultant(long fcId) {
        return financialConsultantJapRepository.findById(fcId).orElseThrow(() -> new EntityNotFoundException("해당 설계사는 존재하지 않습니다."));
    }

}
