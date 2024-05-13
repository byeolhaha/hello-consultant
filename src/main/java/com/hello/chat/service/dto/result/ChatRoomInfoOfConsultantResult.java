package com.hello.chat.service.dto.result;

import com.hello.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryResponse;
import com.hello.chat.repository.chatroom.dto.ChatRoomGetInfoOfConsultant;

import java.time.LocalDateTime;

public record ChatRoomInfoOfConsultantResult(
    long chatRoomId,
    String contents,
    LocalDateTime messageCreatedAt,
    long notReadCount,
    String foreignerName,
    LocalDateTime chatRoomCreatedAt
) {
    public static ChatRoomInfoOfConsultantResult to(
        ChatRoomGetInfoOfConsultant chatRoomInfo,
        ChatMessageRecentGetRepositoryResponse response
    ) {
        return new ChatRoomInfoOfConsultantResult(
            chatRoomInfo.getChatRoomId(),
            response.contents(),
            response.createdAt(),
            response.notReadCount(),
            chatRoomInfo.getName(),
            chatRoomInfo.getCreatedAt()
        );
    }
}
