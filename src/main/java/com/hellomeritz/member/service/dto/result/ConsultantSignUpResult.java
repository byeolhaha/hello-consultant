package com.hellomeritz.member.service.dto.result;

public record ConsultantSignUpResult(
    Long fcId
) {
    public static ConsultantSignUpResult to(Long fcId) {
        return new ConsultantSignUpResult(fcId);
    }
}
