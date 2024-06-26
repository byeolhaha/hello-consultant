package com.hello.chat.repository.chatentry.dto;

import com.hello.chat.repository.chatentry.ChatRoomEntry;

public record ChatRoomEntryAddRepositoryRequest(
    long chatRoomId,
    long userId,
    boolean isFC,
    String sessionId
) {

    public ChatRoomEntry toChatRoomEntry() {
        return new ChatRoomEntry(userId, isFC, sessionId);
    }
}
