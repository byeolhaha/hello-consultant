package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageTranslateTextResult(
    String originContents,
    String translatedContents,
    LocalDateTime createdAt
) {

    public static ChatMessageTranslateTextResult to(
        ChatMessage chatMessage,
        ChatMessage translatedChatMessage) {
        return new ChatMessageTranslateTextResult(
            chatMessage.getContents(),
            translatedChatMessage.getContents(),
            chatMessage.getCreatedAt()
        );
    }

}
