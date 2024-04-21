public record FcInfoGetParam(
        Long userId
) {
    public static FcInfoGetParam to(Long userId) {
        return new FcInfoGetParam(userId);
    }
}
