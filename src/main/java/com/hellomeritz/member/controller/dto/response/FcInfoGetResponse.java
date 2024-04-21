public record FcInfoGetResponse(
        Long financialConsultantId,
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
)
{
    public static FcInfoGetResponse of(FcInfoResult result) {
        return new FcInfoResult(
                result.financialConsultantId(),
                result.phoneNumber(),
                result.name(),
                result.profileUrl(),
                result.introduceMessage()

        );
    }
}