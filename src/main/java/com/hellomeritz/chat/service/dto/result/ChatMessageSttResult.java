package com.hellomeritz.chat.service.dto.result;

import java.time.LocalDateTime;

public record ChatMessageSttResult(
    String audioUrl,
    String speechToText,
    String createdAt
) {
    public static ChatMessageSttResult to(
        String audioUrl,
        String speechToText,
        LocalDateTime createdAt){
        return new ChatMessageSttResult(
            audioUrl,
            speechToText,
            createdAt.toString()
        );
    }
}
