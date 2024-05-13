package com.hellomeritz.member.service.dto.param;

public record FinancialConsultantChangeStateParam(
    long fcId
) {
    public static FinancialConsultantChangeStateParam to(
        Long fcId
    ) {
        return new FinancialConsultantChangeStateParam(fcId);
    }
}
