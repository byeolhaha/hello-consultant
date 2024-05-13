package com.hello.chat.service.dto.param;

import com.hello.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;

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
