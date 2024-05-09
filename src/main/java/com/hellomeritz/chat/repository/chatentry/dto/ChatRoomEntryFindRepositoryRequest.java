package com.hellomeritz.chat.repository.chatentry.dto;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;

public record ChatRoomEntryFindRepositoryRequest(
    long chatRoomId,
    long userId,
    boolean isFC
) {
    public ChatRoomEntry toChatRoomEntry() {
        return new ChatRoomEntry(userId, isFC);
    }
}
