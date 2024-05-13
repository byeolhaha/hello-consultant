package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.UserCheckIsFcResult;

public record UserCheckIsFcResponse(
        boolean isFC
) {
    public static UserCheckIsFcResponse to(UserCheckIsFcResult result) {
        return new UserCheckIsFcResponse(result.isFC());
    }
}
