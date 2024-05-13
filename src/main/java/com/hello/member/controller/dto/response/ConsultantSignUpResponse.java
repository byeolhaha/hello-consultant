package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ConsultantSignUpResult;

public record ConsultantSignUpResponse(
    long fcId
) {
    public static ConsultantSignUpResponse to(ConsultantSignUpResult result) {
        return new ConsultantSignUpResponse(result.fcId());
    }
}
