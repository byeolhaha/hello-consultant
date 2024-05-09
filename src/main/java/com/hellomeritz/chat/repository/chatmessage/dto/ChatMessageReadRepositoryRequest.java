package com.hellomeritz.chat.repository.chatmessage.dto;

public record ChatMessageReadRepositoryRequest(
    long chatRoomId,
    boolean isFC
) {
}
