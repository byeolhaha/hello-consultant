package com.hello.member.service.dto.result;

public record ForeignInfoSaveResult(
        Long userId
) {
    public static ForeignInfoSaveResult to(
            Long userId
    ) {
        return new ForeignInfoSaveResult(
                userId
        );
    }
}
