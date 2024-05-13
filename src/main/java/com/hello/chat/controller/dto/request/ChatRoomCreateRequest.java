package com.hello.chat.controller.dto.request;

import com.hello.chat.service.dto.param.ChatRoomCreateParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomCreateRequest(
        @NotNull(message = "fcId는 null이거나 빈값일 수 없습니다.")
        @Positive(message = "fcId는 0이거나 음수 일 수 없습니다.")
        Long fcId,

        @NotNull(message = "userId는 null이거나 빈값일 수 없습니다.")
        @Positive(message = "userId는 0이거나 음수 일 수 없습니다.")
        Long userId
) {
    public ChatRoomCreateParam toChatRoomCreateRequest() {
        return new ChatRoomCreateParam(
                fcId,
                userId
        );
    }
}
