package com.hellomeritz.member.service.dto.param;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import com.hellomeritz.member.domain.Foreigner;

import java.time.LocalDate;

public record ForeignInfoSaveParam(
        Long userId,
        SourceLanguage language,
        VisaType visaType,
        boolean hasResidentCard,
        String birthDate
) {
    public Foreigner toForeigner(
            String macAddress
    ) {
        return Foreigner.of(
                userId,
                macAddress,
                language,
                visaType,
                hasResidentCard,
                LocalDate.parse(birthDate)
        );
    }
}