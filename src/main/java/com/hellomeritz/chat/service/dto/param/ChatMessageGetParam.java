package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;

public record ChatMessageGetParam(
    long myId,
    String nextChatMessageId,
    long chatRoomId
) {
    public ChatMessageGetRepositoryRequest toChatMessageGetRepositoryRequest(int pageSize) {
        return new ChatMessageGetRepositoryRequest(
            nextChatMessageId,
            chatRoomId,
            pageSize
        );
    }
}
