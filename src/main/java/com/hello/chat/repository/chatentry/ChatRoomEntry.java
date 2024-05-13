package com.hello.chat.repository.chatentry;

public record ChatRoomEntry(
    long userId,
    boolean isFC,
    String sessionId
) {
}
