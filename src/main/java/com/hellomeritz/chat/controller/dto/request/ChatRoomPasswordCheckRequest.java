package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatRoomPasswordCheckParam;
import jakarta.validation.constraints.NotBlank;

public record ChatRoomPasswordCheckRequest(
        @NotBlank(message = "chatRoomPassword은 빈값일 수 없습니다.")
        String chatRoomPassword
) {
    public ChatRoomPasswordCheckParam toChatRoomPasswordCheckParam(long chatRoomId) {
        return new ChatRoomPasswordCheckParam(chatRoomId, chatRoomPassword);
    }

}
