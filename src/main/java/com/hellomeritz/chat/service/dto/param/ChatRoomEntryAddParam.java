package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryAddRepositoryRequest;

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
