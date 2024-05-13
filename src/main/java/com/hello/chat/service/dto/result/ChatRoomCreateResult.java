package com.hello.chat.service.dto.result;

import com.hello.chat.domain.ChatRoom;

public record ChatRoomCreateResult(
    long chatRoomId
) {

    public static ChatRoomCreateResult to(ChatRoom chatRoom) {
        return new ChatRoomCreateResult(chatRoom.getChatRoomId());
    }
}
