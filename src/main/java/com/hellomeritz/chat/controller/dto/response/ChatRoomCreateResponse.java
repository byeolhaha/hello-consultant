package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;

public record ChatRoomCreateResponse(
    long chatRoomId
) {
    public static ChatRoomCreateResponse to(ChatRoomCreateResult result) {
        return new ChatRoomCreateResponse(
            result.chatRoomId()
        );
    }
}
