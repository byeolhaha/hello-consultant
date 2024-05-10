package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryRequest;

public record ChatRoomInfoOfConsultantParam(
    long userId
) {
    public ChatMessageRecentGetRepositoryRequest toChatMessageRecentGetRepositoryRequest(Long chatRoomId) {
        return new ChatMessageRecentGetRepositoryRequest(chatRoomId, true);
    }
}
