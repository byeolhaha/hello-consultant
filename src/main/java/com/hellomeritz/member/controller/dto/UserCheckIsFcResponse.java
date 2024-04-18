package com.hellomeritz.member.controller.dto;

import com.hellomeritz.member.service.dto.result.UserCheckIsFcResult;

public record UserCheckIsFcResponse(
        boolean isFC
) {
    public static UserCheckIsFcResponse to(UserCheckIsFcResult result) {
        return new UserCheckIsFcResponse(result.isFC());
    }
}
