package com.hello.member.repository.fc;

import com.hello.member.domain.FinancialConsultant;

import java.util.List;


public interface FinancialConsultantRepository {

    FinancialConsultant getFinancialConsultant(long fcId);

    FinancialConsultant save(FinancialConsultant financialConsultant);

    List<FinancialConsultant> getFinancialConsultantWithAvailable();

    FinancialConsultant getByEmployeeNumber (String employeeNumber);

}
