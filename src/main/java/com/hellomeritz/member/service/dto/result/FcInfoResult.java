package com.hellomeritz.member.service.dto.result;

public record FcInfoResult(
        Long financialConsultantId,
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
)
{
    public static FcInfoResult of(FinancialConsultant financialConsultant) {
        return new FcInfoResult(
                financialConsultant.getfinancialConsultantId(),
                financialConsultant.getPhoneNumber().name(),
                financialConsultant.getName.name(),
                financialConsultant.getProfileUrl().name(),
                financialConsultant.getIntroduceMessage().name()

    );
    }
}