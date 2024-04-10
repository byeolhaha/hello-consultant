package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatMessageType;

public record ChatMessageTextParam(
    String contents,
    long userId,
    boolean isFC
) {
    public ChatMessage toChatMessage() {
        return ChatMessage.of(
            contents,
            ChatMessageType.TEXT.name(),
            userId,
            isFC
        );
    }
}
