package com.hellomeritz.member.service.dto.result;

public record ForeignerInfoResult(
        Long userId,
        String sourceLanguage,
        String visaType,
        boolean hasResidentCard,
        String birthDate
) {

    public static ForeignerInfoResult of(Foreigner foreigner) {
        return new ForeignerInfoResult(
                foreigner.getUserId(),
                foreigner.getSourceLanguage().name(),
                foreigner.getVisaType.name(),
                foreigner.getHasResidentCard(),
                LocalDate.parse(foreigner.getBirthDate())
        );
    }
}