package com.hellomeritz.member.service.dto.param;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import com.hellomeritz.member.domain.BirthDate;
import com.hellomeritz.member.domain.Foreigner;

public record ForeignInfoSaveParam(
        Long userId,
        SourceLanguage language,
        VisaType visaType,
        boolean hasResidentCard,
        BirthDate birthDate
) {
    public Foreigner toForeigner() {
        return Foreigner.of(
                userId,
                language,
                visaType,
                hasResidentCard,
                birthDate
        );
    }
}