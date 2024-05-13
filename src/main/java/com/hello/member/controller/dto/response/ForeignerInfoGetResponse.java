package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ForeignerInfoResult;

public record ForeignerInfoGetResponse(
    Long userId,
    String sourceLanguage,
    String visaType,
    boolean hasResidentCard,
    String birthDate,
    String name
) {

    public static ForeignerInfoGetResponse of(ForeignerInfoResult result) {
        return new ForeignerInfoGetResponse(
            result.userId(),
            result.sourceLanguage(),
            result.visaType(),
            result.hasResidentCard(),
            result.birthDate(),
            result.name()
        );
    }
}