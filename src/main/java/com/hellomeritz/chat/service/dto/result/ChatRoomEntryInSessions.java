package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.repository.chatsession.ChatSession;

public record ChatRoomEntryInSessions(
    long userId,
    boolean isFC
) {
    public static ChatRoomEntryInSessions to(ChatSession chatSession) {
        return new ChatRoomEntryInSessions(
            chatSession.getUserId(),
            chatSession.getIsFC()
        );
    }
}
