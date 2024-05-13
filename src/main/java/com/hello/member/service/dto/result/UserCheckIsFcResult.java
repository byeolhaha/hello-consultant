package com.hello.member.service.dto.result;

public record UserCheckIsFcResult(
        boolean isFC
) {
    public static UserCheckIsFcResult to(boolean isFC) {
        return new UserCheckIsFcResult(!isFC);
    }
}
