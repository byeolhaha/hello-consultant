package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatRoomCreateParam;

public record ChatRoomCreateRequest(
    long fcId,
    long userId
) {
    public ChatRoomCreateParam toChatRoomCreateRequest() {
        return new ChatRoomCreateParam(
            fcId,
            userId
        );
    }
}
