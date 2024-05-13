package com.hello.chat.controller.dto.response;

import com.hello.chat.service.dto.result.ChatRoomUserInfoResult;

public record ChatRoomUserInfoResponse(
        long userId,
        long fcId
) {
    public static ChatRoomUserInfoResponse to(
            ChatRoomUserInfoResult result
    ) {
        return new ChatRoomUserInfoResponse(
                result.userId(),
                result.fcId()
        );
    }
}
