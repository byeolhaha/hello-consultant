package com.hello.chat.controller.dto.response;

import com.hello.chat.service.dto.result.ChatRoomCreateResult;

public record ChatRoomCreateResponse(
    long chatRoomId
) {
    public static ChatRoomCreateResponse to(ChatRoomCreateResult result) {
        return new ChatRoomCreateResponse(
            result.chatRoomId()
        );
    }
}
