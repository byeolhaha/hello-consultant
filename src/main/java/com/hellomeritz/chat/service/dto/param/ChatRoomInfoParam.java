package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryRequest;

public record ChatRoomInfoParam(
    long userId,
    boolean isFC
) {
    public ChatMessageRecentGetRepositoryRequest toChatMessageRecentGetRepositoryRequest(Long chatRoomId) {
     return new ChatMessageRecentGetRepositoryRequest(chatRoomId, isFC);
    }
}
