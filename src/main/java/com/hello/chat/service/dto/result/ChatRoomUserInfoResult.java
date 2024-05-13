package com.hello.chat.service.dto.result;

import com.hello.chat.repository.chatroom.dto.ChatRoomUserInfo;

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
