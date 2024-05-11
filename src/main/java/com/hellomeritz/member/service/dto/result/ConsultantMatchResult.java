package com.hellomeritz.member.service.dto.result;

public record ConsultantMatchResult(
    Long fcId
) {
    public static ConsultantMatchResult to(
        Long fcId
    ){
        return new ConsultantMatchResult(fcId);
    }
}
