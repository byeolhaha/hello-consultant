package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;

public record ChatSessionAddRepositoryRequest(
    String sessionId,
    long userId,
    boolean isFC
) {
    public ChatRoomEntry toChatRoomEntry() {
        return new ChatRoomEntry(userId, isFC);
    }
}
