package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ForeignCreateResult;

public record ForeignCreateResponse(
        long userId
) {
    public static ForeignCreateResponse to(ForeignCreateResult result) {
        return new ForeignCreateResponse(
                result.userId()
        );
    }
}
