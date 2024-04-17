package com.hellomeritz.member.service.dto.result;

public record ForeignUserIdResult(
        Long userId
) {
    public static ForeignUserIdResult to(
            Long userId
    ) {
        return new ForeignUserIdResult(userId);
    }
}
