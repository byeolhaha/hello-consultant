package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomUserInfo;

public record ChatRoomUserInfoResult(
        Long userId,
        Long fcId
) {
    public static ChatRoomUserInfoResult to(ChatRoomUserInfo chatRoomUserInfo) {
        return new ChatRoomUserInfoResult(
                chatRoomUserInfo.getForeignerId(),
                chatRoomUserInfo.getFcId()
        );
    }
}
