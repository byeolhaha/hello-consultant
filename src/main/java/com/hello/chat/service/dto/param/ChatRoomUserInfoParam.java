package com.hello.chat.service.dto.param;

public record ChatRoomUserInfoParam(
        long chatRoomId
) {
    public static ChatRoomUserInfoParam to(long chatRoomId) {
        return new ChatRoomUserInfoParam(chatRoomId);
    }
}
