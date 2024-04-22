package com.hellomeritz.chat.service.dto.result;

import java.time.LocalDateTime;

public record ChatMessageSttResult(
    String textBySpeech,
    String createdAt,
    String sttProvider
) {
    public static ChatMessageSttResult to(
        String speechToText,
        LocalDateTime createdAt,
        String sttProvider){
        return new ChatMessageSttResult(
            speechToText,
            createdAt.toString(),
            sttProvider
        );
    }
}
