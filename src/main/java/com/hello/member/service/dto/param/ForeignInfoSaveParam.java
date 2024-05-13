package com.hello.member.service.dto.param;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.VisaType;
import com.hello.member.domain.BirthDate;
import com.hello.member.domain.Foreigner;

public record ForeignInfoSaveParam(
    SourceLanguage language,
    VisaType visaType,
    boolean hasResidentCard,
    BirthDate birthDate,
    String name
) {
    public Foreigner toForeigner() {
        return Foreigner.of(
            language,
            visaType,
            hasResidentCard,
            birthDate,
            name
        );
    }
}