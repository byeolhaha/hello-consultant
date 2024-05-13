package com.hello.member.controller.dto.request;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.VisaType;
import com.hello.member.domain.BirthDate;
import com.hello.member.service.dto.param.ForeignInfoSaveParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ForeignInfoSaveRequest(
    @NotBlank(message = "사용자의 언어는 null일 수 없습니다.")
    String language,

    @NotBlank(message = "visaType은 null일 수 없습니다.")
    String visaType,

    @NotNull(message = "거주증 여부는 null일 수 없습니다.")
    Boolean hasResidentCard,

    @NotBlank(message = "생년월일은 null일 수 없습니다.")
    String birthDate,

    @NotBlank(message = "이름은 null이거나 빈 값일 수 없습니다.")
    String name


) {
    public ForeignInfoSaveParam toForeignInfoSaveParam() {
        return new ForeignInfoSaveParam(
            SourceLanguage.findSourceLanguage(language),
            VisaType.findVisaType(visaType),
            hasResidentCard,
            BirthDate.of(birthDate),
            name
        );
    }

    @AssertTrue(message = "sourceLang 형식이 enum 형식에 맞지 않습니다.")
    public boolean checkSourceLangFormat() {
        return SourceLanguage.checkFormat(language);
    }

    @AssertTrue(message = "visaType 형식이 enum 형식에 맞지 않습니다.")
    public boolean checkVisaTypeFormat() {
        return VisaType.checkTranslatorFormat(visaType);
    }

}
