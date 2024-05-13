package com.hellomeritz.member.controller.dto.response;

import com.hellomeritz.member.service.dto.result.ConsultantSignUpResult;

public record ConsultantSignUpResponse(
    long fcId
) {
    public static ConsultantSignUpResponse to(ConsultantSignUpResult result) {
        return new ConsultantSignUpResponse(result.fcId());
    }
}
