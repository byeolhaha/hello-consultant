package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ForeignInfoSaveResult;

public record ForeignInfoSaveResponse(
        long userId
) {
    public static ForeignInfoSaveResponse to(ForeignInfoSaveResult result) {
        return new ForeignInfoSaveResponse(
                result.userId()
        );
    }
}
