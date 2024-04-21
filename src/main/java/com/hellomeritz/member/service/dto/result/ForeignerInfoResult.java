package com.hellomeritz.member.service.dto.result;

import com.hellomeritz.member.domain.Foreigner;

public record ForeignerInfoResult(
        Long userId,
        String sourceLanguage,
        String visaType,
        boolean hasResidentCard,
        String birthDate
) {

    public static ForeignerInfoResult of(Foreigner foreigner) {
        return new ForeignerInfoResult(
                foreigner.getForeignerId(),
                foreigner.getLanguage().name(),
                foreigner.getVisaType().name(),
                foreigner.isHasResidentCard(),
                foreigner.getBirthDate().toString()
        );
    }
}