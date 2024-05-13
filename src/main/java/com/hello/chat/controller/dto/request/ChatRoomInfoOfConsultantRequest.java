package com.hello.chat.controller.dto.request;

import com.hello.chat.service.dto.param.ChatRoomInfoOfConsultantParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomInfoOfConsultantRequest(
    @NotNull(message = "userId는 null일 수 없습니다.")
    @Positive(message = "userId는 양수여야 합니다.")
    Long userId
) {
    public ChatRoomInfoOfConsultantParam toChatRoomInfoParam() {
        return new ChatRoomInfoOfConsultantParam(
            userId
        );
    }
}
