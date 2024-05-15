package com.hello.chat.controller.dto.request;

import com.hello.chat.service.dto.param.ChatRoomInfoOfForeignerParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomInfoOfForeignerRequest(
    @NotNull(message = "userId는 null일 수 없습니다.")
    @Positive(message = "userId는 양수여야 합니다.")
    Long userId
) {
    public ChatRoomInfoOfForeignerParam toChatRoomInfoParam() {
        return new ChatRoomInfoOfForeignerParam(
            userId
        );
    }

    public static ChatRoomInfoOfForeignerParam toChatRoomInfoParam(Long userId) {
        return new ChatRoomInfoOfForeignerParam(
            userId
        );
    }
}
