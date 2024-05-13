package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.FcInfoResult;

public record FcInfoGetResponse(
        Long financialConsultantId,
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
)
{
    public static FcInfoGetResponse of(FcInfoResult result) {
        return new FcInfoGetResponse(
                result.financialConsultantId(),
                result.phoneNumber(),
                result.name(),
                result.profileUrl(),
                result.introduceMessage()
        );
    }
}