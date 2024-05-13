package com.hello.chat.controller.dto.response;

public record ChatRoomPasswordCheckResponse(
        Boolean isMyUser
) {
    public static ChatRoomPasswordCheckResponse to(boolean isMyUser) {
        return new ChatRoomPasswordCheckResponse(isMyUser);
    }
}
