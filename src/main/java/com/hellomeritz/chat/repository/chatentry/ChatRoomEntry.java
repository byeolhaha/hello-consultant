package com.hellomeritz.chat.repository.chatentry;

public record ChatRoomEntry(
    long userId,
    boolean isFC,
    String sessionId
) {
}
