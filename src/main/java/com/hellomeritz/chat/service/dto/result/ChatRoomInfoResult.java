package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryResponse;

import java.time.LocalDateTime;

public record ChatRoomInfoResult(
    long chatRoomId,
    String contents,
    LocalDateTime createdAt,
    long notReadCount
) {
    public static ChatRoomInfoResult to(
        Long chatRoomId,
        ChatMessageRecentGetRepositoryResponse response
    ) {
        return new ChatRoomInfoResult(
            chatRoomId,
            response.contents(),
            response.createdAt(),
            response.notReadCount()
        );
    }
}
