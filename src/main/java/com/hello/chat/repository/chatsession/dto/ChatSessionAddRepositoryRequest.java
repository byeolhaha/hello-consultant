package com.hello.chat.repository.chatsession.dto;

import com.hello.chat.repository.chatsession.ChatSession;

public record ChatSessionAddRepositoryRequest(
    String sessionId,
    long userId,
    boolean isFC
) {
    public ChatSession toChatRoomEntry() {
        return ChatSession.to(userId, isFC);
    }
}
