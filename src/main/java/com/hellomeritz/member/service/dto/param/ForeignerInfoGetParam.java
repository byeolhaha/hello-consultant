package com.hellomeritz.member.service.dto.param;

public record ForeignerInfoGetParam(
    Long userId
) {
    public static ForeignerInfoGetParam to(Long userId) {
        return new ForeignerInfoGetParam(userId);
    }
}
