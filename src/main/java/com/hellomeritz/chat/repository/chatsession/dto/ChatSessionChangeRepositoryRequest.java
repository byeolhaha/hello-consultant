package com.hellomeritz.chat.repository.chatsession.dto;

public record ChatSessionChangeRepositoryRequest(
    long chatRoomId,
    String sessionId
) {
}
