package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatRoomInfoParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomInfoRequest(

    @NotNull(message = "userId는 null일 수 없습니다.")
    @Positive(message = "userId는 양수여야 합니다.")
    Long userId,

    @NotNull(message = "isFC는 null일 수 없습니다.")
    Boolean isFC
) {
    public ChatRoomInfoParam toChatRoomInfoParam() {
        return new ChatRoomInfoParam(
            userId,
            isFC
        );
    }
}
