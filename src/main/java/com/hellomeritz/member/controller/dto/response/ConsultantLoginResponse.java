package com.hellomeritz.member.controller.dto.response;

import com.hellomeritz.member.service.dto.result.ConsultantLoginResult;

public record ConsultantLoginResponse(
    boolean isMyConsultant,
    long fcId
) {

    public static ConsultantLoginResponse to(ConsultantLoginResult result) {
        return new ConsultantLoginResponse(
            result.isMyConsultant(),
            result.fcId()
        );
    }
}
