package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageReadRepositoryRequest;

public record ChatRoomEnterParam(
    long chatRoomId,
    boolean isFC
) {
    public ChatMessageReadRepositoryRequest toChatMessageReadRepositoryRequest() {
        return new ChatMessageReadRepositoryRequest(
            chatRoomId,
            isFC
        );
    }
}
