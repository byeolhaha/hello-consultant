package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;

import java.util.List;

public record ForeignInfoSaveParam(
        SourceLanguage language,
        VisaType visaType,
        boolean hasResidentCard,
        List<String> interestedInsurances,
        boolean isFemale,
        String birth


) {
}
