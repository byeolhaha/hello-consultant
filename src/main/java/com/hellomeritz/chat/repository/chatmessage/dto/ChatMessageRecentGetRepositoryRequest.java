package com.hellomeritz.chat.repository.chatmessage.dto;

public record ChatMessageRecentGetRepositoryRequest(
    long chatRoomId,
    boolean isFC
) {
}
