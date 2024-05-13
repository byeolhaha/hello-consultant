package com.hellomeritz.member.service.dto.param;

import com.hellomeritz.member.domain.FinancialConsultant;
import com.hellomeritz.global.encryption.dto.EncryptionRequest;
import com.hellomeritz.global.encryption.dto.PasswordMatchRequest;

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
