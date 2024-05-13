package com.hello.chat.service.dto.result;

import java.time.LocalDateTime;

public record ChatAudioUploadResult(
        String audioUrl,
        String createdAt
) {
    public static ChatAudioUploadResult to(
            String audioUrl,
            LocalDateTime createdAt
    ) {
        return new ChatAudioUploadResult(
                audioUrl,
                createdAt.toString()
        );
    }
}
