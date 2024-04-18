package com.hellomeritz.member.service.dto.result;

public record ForeignInfoSaveResult(
        Long userId,
        String macAddress
) {
    public static ForeignInfoSaveResult to(
            Long userId,
            String macAddress
    ) {
        return new ForeignInfoSaveResult(
                userId,
                macAddress
        );
    }
}
