package com.hellomeritz.chat.repository.chatentry.dto;

public record ChatRoomEntryGetSessionRequest(
    long chatRoomId,
    long userId,
    boolean isFC
) {
}
