package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;

public record ChatSessionChangeParam(
    Long chatRoomId,
    String sessionId
) {
    public ChatSessionChangeRepositoryRequest toChatSessionChangeRepositoryRequest() {
        return new ChatSessionChangeRepositoryRequest(
            chatRoomId,
            sessionId
        );
    }
}
