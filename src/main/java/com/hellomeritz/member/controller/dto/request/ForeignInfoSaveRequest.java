package com.hellomeritz.member.controller.dto.request;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import com.hellomeritz.member.domain.BirthDate;
import com.hellomeritz.member.service.dto.param.ForeignInfoSaveParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record ForeignInfoSaveRequest(
        @NotBlank
        String language,

        @NotBlank
        String visaType,

        boolean hasResidentCard,

        @NotBlank
        String birthDate
) {
    public ForeignInfoSaveParam toForeignInfoSaveParam(Long userId) {
        return new ForeignInfoSaveParam(
                userId,
                SourceLanguage.findSttSourceLanguage(language),
                VisaType.findVisaType(visaType),
                hasResidentCard,
                BirthDate.of(birthDate)
        );
    }

    @AssertTrue(message = "sourceLang 형식이 enum 형식에 맞지 않습니다.")
    public boolean checkSourceLangFormat() {
        return SourceLanguage.checkSttFormat(language);
    }

    @AssertTrue(message = "visaType 형식이 enum 형식에 맞지 않습니다.")
    public boolean checkVisaTypeFormat() {
        return VisaType.checkTranslatorFormat(visaType);
    }

}
