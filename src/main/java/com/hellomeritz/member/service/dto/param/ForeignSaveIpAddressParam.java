package com.hellomeritz.member.service.dto.param;

public record ForeignSaveIpAddressParam(
        Long userId
) {
    public static ForeignSaveIpAddressParam to(Long userId) {
        return new ForeignSaveIpAddressParam(userId);
    }
}
