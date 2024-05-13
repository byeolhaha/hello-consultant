package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ConsultantMatchResult;

public record ConsultantMatchResponse(
    long fcId
) {
    public static ConsultantMatchResponse to(ConsultantMatchResult result){
        return new ConsultantMatchResponse(result.fcId());
    }
}
