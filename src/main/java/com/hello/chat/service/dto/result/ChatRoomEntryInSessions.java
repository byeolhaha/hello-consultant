package com.hello.chat.service.dto.result;

import com.hello.chat.repository.chatsession.ChatSession;

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
