package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryRequest;

public record ChatRoomInfoOfForeignerParam(
    long userId
) {
    public ChatMessageRecentGetRepositoryRequest toChatMessageRecentGetRepositoryRequest(Long chatRoomId) {
     return new ChatMessageRecentGetRepositoryRequest(chatRoomId, false);
    }
}
