package com.hello.chat.repository.chatentry.dto;

public record ChatRoomEntryDeleteRepositoryRequest(
    long chatRoomId,
    String sessionId
) {
}
