package com.hello.member.service.dto.param;

public record UserCheckIsFcParam(
        Long userId
) {
    public static UserCheckIsFcParam to(Long userId) {
        return new UserCheckIsFcParam(userId);
    }
}
