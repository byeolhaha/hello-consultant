package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatRoomUserInfoResult;

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
