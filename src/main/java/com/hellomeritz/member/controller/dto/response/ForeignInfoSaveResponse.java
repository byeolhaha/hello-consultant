package com.hellomeritz.member.controller.dto.response;

import com.hellomeritz.member.service.dto.result.ForeignInfoSaveResult;

public record ForeignInfoSaveResponse(
        long userId
) {
    public static ForeignInfoSaveResponse to(ForeignInfoSaveResult result) {
        return new ForeignInfoSaveResponse(
                result.userId()
        );
    }
}
