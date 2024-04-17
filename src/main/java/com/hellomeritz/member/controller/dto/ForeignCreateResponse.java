package com.hellomeritz.member.controller.dto;

import com.hellomeritz.member.service.dto.result.ForeignCreateResult;

public record ForeignCreateResponse(
        long userId
) {
    public static ForeignCreateResponse to(ForeignCreateResult result) {
        return new ForeignCreateResponse(
                result.userId()
        );
    }
}
