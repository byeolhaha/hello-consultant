package com.hellomeritz.member.service.dto.param;

public record FinancialConsultantInfoGetParam(
    Long userId
) {
    public static FinancialConsultantInfoGetParam to(Long userId) {
        return new FinancialConsultantInfoGetParam(userId);
    }
}
