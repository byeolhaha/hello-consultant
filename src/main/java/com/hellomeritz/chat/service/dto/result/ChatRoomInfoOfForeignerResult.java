package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryResponse;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomGetInfoOfForeigner;

import java.time.LocalDateTime;

public record ChatRoomInfoOfForeignerResult(
    long chatRoomId,
    String contents,
    LocalDateTime messageCreatedAt,
    long notReadCount,
    String consultantName,
    String profileUrl
) {
    public static ChatRoomInfoOfForeignerResult to(
        ChatRoomGetInfoOfForeigner chatRoomInfo,
        ChatMessageRecentGetRepositoryResponse response
    ) {
        return new ChatRoomInfoOfForeignerResult(
            chatRoomInfo.getChatRoomId(),
            response.contents(),
            response.createdAt(),
            response.notReadCount(),
            chatRoomInfo.getName(),
            chatRoomInfo.getProfileUrl()
        );
    }
}
