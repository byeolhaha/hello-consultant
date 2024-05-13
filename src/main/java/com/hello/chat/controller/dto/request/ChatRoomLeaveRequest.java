package com.hello.chat.controller.dto.request;

import com.hello.chat.service.dto.param.ChatRoomLeaveParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomLeaveRequest(
    @Positive(message = "userId는 양수여야 합니다.")
    @NotNull(message = "userId는 null일 수 없습니다.")
    Long userId,

    @NotNull(message = "isFC는 null일 수 없습니다.")
    Boolean isFC
) {

    public ChatRoomLeaveParam toChatRoomLeaveParam(Long chatRoomId) {
        return new ChatRoomLeaveParam(
            chatRoomId,
            userId,
            isFC
        );
    }
}
