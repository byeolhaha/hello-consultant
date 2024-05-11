package com.hellomeritz.member.controller.dto.response;

import com.hellomeritz.member.service.dto.result.ConsultantMatchResult;

public record ConsultantMatchResponse(
    long fcId
) {
    public static ConsultantMatchResponse to(ConsultantMatchResult result){
        return new ConsultantMatchResponse(result.fcId());
    }
}
