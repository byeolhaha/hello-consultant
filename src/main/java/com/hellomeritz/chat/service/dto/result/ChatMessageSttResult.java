package com.hellomeritz.chat.service.dto.result;

import java.time.LocalDateTime;

public record ChatMessageSttResult(
    String textBySpeech,
    String createdAt
) {
    public static ChatMessageSttResult to(
        String speechToText,
        LocalDateTime createdAt){
        return new ChatMessageSttResult(
            speechToText,
            createdAt.toString()
        );
    }
}
