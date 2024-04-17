package com.hellomeritz.member.controller.dto;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import com.hellomeritz.member.service.dto.param.ForeignInfoSaveParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ForeignInfoSaveRequest(
        @Positive
        @NotNull
        Long userId,

        @NotBlank
        String language,

        @NotBlank
        String visaType,

        boolean hasResidentCard,

        @NotBlank
        String birthDate
) {
    public ForeignInfoSaveParam toForeignInfoSaveParam() {
        return new ForeignInfoSaveParam(
                userId,
                SourceLanguage.findSttSourceLanguage(language),
                VisaType.findVisaType(visaType),
                hasResidentCard,
                birthDate
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
