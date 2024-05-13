package com.hellomeritz.member.service.dto.result;

public record ConsultantLoginResult(
    boolean isMyConsultant,
    long fcId
) {
    public static ConsultantLoginResult to(
        Boolean isMyConsultant,
        Long fcId
    ){
        return new ConsultantLoginResult(
            isMyConsultant,
            fcId
        );
    }
}
