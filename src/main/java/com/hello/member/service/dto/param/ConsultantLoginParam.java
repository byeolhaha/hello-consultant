package com.hello.member.service.dto.param;

import com.hello.member.domain.FinancialConsultant;
import com.hello.global.encryption.dto.EncryptionRequest;
import com.hello.global.encryption.dto.PasswordMatchRequest;

import java.time.LocalDateTime;

public record ConsultantLoginParam(
    String employeeNumber,
    String consultantPassword
) {
    public PasswordMatchRequest toPasswordMatchRequest(FinancialConsultant financialConsultant) {
        return new PasswordMatchRequest(
            consultantPassword.toCharArray(),
            financialConsultant.getConsultantPassword(),
            financialConsultant.getSalt()
        );
    }

    public EncryptionRequest toEncryptionRequest(String clientIp) {
        return new EncryptionRequest(
            consultantPassword,
            clientIp,
            LocalDateTime.now()
        );
    }

}
