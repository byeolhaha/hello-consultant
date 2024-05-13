package com.hello.chat.service.dto.param;

import com.hello.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryRequest;

public record ChatRoomInfoOfConsultantParam(
    long userId
) {
    public ChatMessageRecentGetRepositoryRequest toChatMessageRecentGetRepositoryRequest(Long chatRoomId) {
        return new ChatMessageRecentGetRepositoryRequest(chatRoomId, true);
    }
}
