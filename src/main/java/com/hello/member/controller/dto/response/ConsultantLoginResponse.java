package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ConsultantLoginResult;

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
