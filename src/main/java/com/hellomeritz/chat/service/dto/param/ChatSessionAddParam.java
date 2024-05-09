package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatsession.dto.ChatSessionAddRepositoryRequest;

public record ChatSessionAddParam(
    String sessionId,
    Long userId,
    Boolean isFC
) {
    public ChatSessionAddRepositoryRequest toChatSessionAddRepositoryRequest() {
        return new ChatSessionAddRepositoryRequest(
            sessionId,
            userId,
            isFC
        );
    }
}
