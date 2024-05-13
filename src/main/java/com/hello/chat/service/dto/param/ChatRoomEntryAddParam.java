package com.hello.chat.service.dto.param;

import com.hello.chat.repository.chatentry.dto.ChatRoomEntryAddRepositoryRequest;

public record ChatRoomEntryAddParam(
    Long chatRoomId,
    Long userId,
    Boolean isFC,
    String sessionId
) {
    public ChatRoomEntryAddRepositoryRequest toChatRoomEntryAddRepositoryRequest() {
        return new ChatRoomEntryAddRepositoryRequest(
            chatRoomId,
            userId,
            isFC,
            sessionId
        );
    }
}
