package com.hello.chat.service.dto.param;

import com.hello.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;

public record ChatMessageGetParam(
    long myId,
    String nextChatMessageId,
    long chatRoomId,
    boolean isFC
) {
    public ChatMessageGetRepositoryRequest toChatMessageGetRepositoryRequest(int pageSize) {
        return new ChatMessageGetRepositoryRequest(
            nextChatMessageId,
            chatRoomId,
            pageSize
        );
    }
}
