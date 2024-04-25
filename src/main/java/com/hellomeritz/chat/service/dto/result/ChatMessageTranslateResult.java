package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageTranslateResult(
    String originContents,
    String translatedContents,
    LocalDateTime createdAt
) {

    public static ChatMessageTranslateResult to(
        String chatMessage,
        ChatMessage translatedChatMessage) {
        return new ChatMessageTranslateResult(
            chatMessage,
            translatedChatMessage.getContents(),
            LocalDateTime.now()
        );
    }

}
