public record ForeignerInfoGetResponse(
        Long userId,
        String sourceLanguage,
        String visaType,
        boolean hasResidentCard,
        String birthDate
) {

    public static ForeignerInfoGetResponse of(ForeignerInfoResult result) {
        return new ForeignerInfoGetResponse(
                result.userId(),
                result.sourceLanguage(),
                result.visaType(),
                result.hasResidentCard(),
                result.birthDate()
        );
    }
}