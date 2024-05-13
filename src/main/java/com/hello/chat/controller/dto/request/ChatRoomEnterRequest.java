package com.hello.chat.controller.dto.request;

import com.hello.chat.service.dto.param.ChatRoomEnterParam;
import jakarta.validation.constraints.NotNull;

public record ChatRoomEnterRequest(

    @NotNull(message = "isFC는 null값 일 수 없습니다.")
    Boolean isFC
) {

    public ChatRoomEnterParam toChatRoomEnterParam(Long chatRoomId) {
        return new ChatRoomEnterParam(chatRoomId, isFC);
    }
}
