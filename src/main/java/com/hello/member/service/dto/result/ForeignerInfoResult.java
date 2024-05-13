package com.hello.member.service.dto.result;

import com.hello.member.domain.Foreigner;

public record ForeignerInfoResult(
    Long userId,
    String sourceLanguage,
    String visaType,
    boolean hasResidentCard,
    String birthDate,
    String name
) {

    public static ForeignerInfoResult of(Foreigner foreigner) {
        return new ForeignerInfoResult(
            foreigner.getForeignerId(),
            foreigner.getLanguage().name(),
            foreigner.getVisaType().name(),
            foreigner.isHasResidentCard(),
            foreigner.getBirthDate().toString(),
            foreigner.getName()
        );
    }
}