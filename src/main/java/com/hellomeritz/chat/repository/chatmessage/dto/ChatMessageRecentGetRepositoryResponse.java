package com.hellomeritz.chat.repository.chatmessage.dto;

import com.hellomeritz.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageRecentGetRepositoryResponse(
    String contents,
    LocalDateTime createdAt,
    long notReadCount
) {
    public static ChatMessageRecentGetRepositoryResponse to(ChatMessage chatMessage, long notReadCount) {
        return new ChatMessageRecentGetRepositoryResponse(
            chatMessage.getContents(),
            chatMessage.getCreatedAt(),
            notReadCount
        );
    }
}
