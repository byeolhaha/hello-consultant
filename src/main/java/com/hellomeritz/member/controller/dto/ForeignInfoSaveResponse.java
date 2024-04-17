package com.hellomeritz.member.controller.dto;

import com.hellomeritz.member.service.dto.result.ForeignInfoSaveResult;

public record ForeignInfoSaveResponse(
        long userId,
        String macAddress
) {
    public static ForeignInfoSaveResponse to(ForeignInfoSaveResult result) {
        return new ForeignInfoSaveResponse(
                result.userId(),
                result.macAddress()
        );
    }
}
