package com.hello.chat.service.dto.param;

import com.hello.chat.repository.chatentry.dto.ChatRoomEntryGetSessionRequest;

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
