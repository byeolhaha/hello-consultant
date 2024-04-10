package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageTranslateTextResult(
    String translatedContents,
    LocalDateTime createdAt
) {

    public static ChatMessageTranslateTextResult to(ChatMessage chatMessage) {
        return new ChatMessageTranslateTextResult(
            chatMessage.getContents(),
            chatMessage.getCreatedAt()
        );
    }

}
