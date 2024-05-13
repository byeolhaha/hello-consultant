package com.hello.chat.repository.chatmessage.dto;

public record ChatMessageGetRepositoryRequest(
    String nextChatMessageId,
    long chatRoomId,
    int pageSize
) {
}
