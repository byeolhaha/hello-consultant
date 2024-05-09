package com.hellomeritz.chat.repository.chatsession.dto;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;
import com.hellomeritz.chat.repository.chatsession.ChatSession;

public record ChatSessionAddRepositoryRequest(
    String sessionId,
    long userId,
    boolean isFC
) {
    public ChatSession toChatRoomEntry() {
        return ChatSession.to(userId, isFC);
    }
}
