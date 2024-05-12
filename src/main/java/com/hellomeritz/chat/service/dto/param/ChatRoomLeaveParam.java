package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryGetSessionRequest;

public record ChatRoomLeaveParam(
    long chatRoomId,
    long userId,
    boolean isFC

) {

    public ChatRoomEntryGetSessionRequest toChatRoomEntryGetSessionRequest() {
        return new ChatRoomEntryGetSessionRequest(
            chatRoomId,
            userId,
            isFC
        );
    }
}
