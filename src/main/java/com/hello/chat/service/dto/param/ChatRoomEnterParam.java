package com.hello.chat.service.dto.param;

import com.hello.chat.repository.chatmessage.dto.ChatMessageReadRepositoryRequest;

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
