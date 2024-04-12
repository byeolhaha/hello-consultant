package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;

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
