package com.hellomeritz.chat.repository.chatentry.dto;

public record ChatRoomEntryDeleteRepositoryRequest(
    long chatRoomId,
    String sessionId
) {
}
