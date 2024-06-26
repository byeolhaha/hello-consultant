package com.hello.member.repository.fc;

import com.hello.member.domain.FinancialConsultant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FinancialConsultantRepositoryImpl implements FinancialConsultantRepository {

    private final FinancialConsultantJapRepository financialConsultantJapRepository;

    public FinancialConsultantRepositoryImpl(FinancialConsultantJapRepository financialConsultantJapRepository) {
        this.financialConsultantJapRepository = financialConsultantJapRepository;
    }

    @Override
    public FinancialConsultant getFinancialConsultant(long fcId) {
        return financialConsultantJapRepository.findById(fcId).orElseThrow(() -> new EntityNotFoundException("해당 설계사는 존재하지 않습니다."));
    }

    @Override
    public FinancialConsultant save(FinancialConsultant financialConsultant) {
        return financialConsultantJapRepository.save(financialConsultant);
    }

    @Override
    public List<FinancialConsultant> getFinancialConsultantWithAvailable() {
        List<FinancialConsultant> financialConsultants = financialConsultantJapRepository.findFinancialConsultant();
        if (financialConsultants.isEmpty()) {
            throw new IllegalStateException("상담 가능한 설계사가 없습니다.");//todo 예외 고치자
        }
        return financialConsultants;
    }

    @Override
    public FinancialConsultant getByEmployeeNumber(String employeeNumber) {
        return financialConsultantJapRepository.findByEmployeeNumber(employeeNumber)
            .orElseThrow(()-> new EntityNotFoundException("해당하는 설계사는 존재하지 않습니다."));
    }

}
