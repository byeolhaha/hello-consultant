package com.hellomeritz.member.service.dto.param;

import com.hellomeritz.global.encryption.PassWord;
import com.hellomeritz.member.domain.FinancialConsultant;
import com.hellomeritz.global.encryption.dto.EncryptionRequest;
import com.hellomeritz.global.encryption.dto.EncryptionResponse;

import java.time.LocalDateTime;

public record ConsultantSignUpParam(
    String employeeNumber,
    PassWord consultantPassword,
    String phoneNumber,
    String name,
    String profileUrl,
    String introduceMessage
) {
    public FinancialConsultant toFinancialConsultant(EncryptionResponse response) {
        return FinancialConsultant.of(
            employeeNumber,
            response.password(),
            response.salt(),
            phoneNumber,
            name,
            profileUrl,
            introduceMessage
        );
    }

    public EncryptionRequest toEncryptionRequest(String clientIp) {
        return new EncryptionRequest(
            consultantPassword.getPassword(),
            clientIp,
            LocalDateTime.now()
        );
    }
}
