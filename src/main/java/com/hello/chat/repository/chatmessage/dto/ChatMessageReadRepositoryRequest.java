package com.hello.chat.repository.chatmessage.dto;

public record ChatMessageReadRepositoryRequest(
    long chatRoomId,
    boolean isFC
) {
}
