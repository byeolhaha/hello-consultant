package com.hellomeritz.member.service.dto.result;

import com.hellomeritz.member.domain.FinancialConsultant;

public record FcInfoResult(
    Long financialConsultantId,
    String phoneNumber,
    String name,
    String profileUrl,
    String introduceMessage
) {
    public static FcInfoResult of(FinancialConsultant financialConsultant) {
        return new FcInfoResult(
            financialConsultant.getFinancialConsultantId(),
            financialConsultant.getPhoneNumber(),
            financialConsultant.getName(),
            financialConsultant.getProfileUrl(),
            financialConsultant.getIntroduceMessage()
        );
    }
}