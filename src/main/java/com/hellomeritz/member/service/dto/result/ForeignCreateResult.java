package com.hellomeritz.member.service.dto.result;

public record ForeignCreateResult(
        Long userId
) {
    public static ForeignCreateResult of(Long userId) {
        return new ForeignCreateResult(userId);
    }
}
