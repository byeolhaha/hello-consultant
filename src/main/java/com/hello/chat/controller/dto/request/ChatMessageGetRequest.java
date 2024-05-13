package com.hello.chat.controller.dto.request;

import com.hello.chat.service.dto.param.ChatMessageGetParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

public record ChatMessageGetRequest(
        @NotNull(message = "myId는 null이거나 빈값일 수 없습니다.")
        @Positive(message = "myId는 0이거나 음수 일 수 없습니다.")
        Long myId,
        String nextChatMessageId,

        @NotNull(message = "isFC는 null일 수 없습니다.")
        Boolean isFC
) {
    private static final String CHAT_MESSAGE_ID_MIN_VALUE = "";

    public ChatMessageGetParam toChatMessageGetParam(long chatRoomId) {
        return new ChatMessageGetParam(
                myId,
                isFirstPage(),
                chatRoomId,
                isFC
        );
    }

    private String isFirstPage() {
        if (Objects.isNull(nextChatMessageId) || nextChatMessageId.isEmpty()) {
            return CHAT_MESSAGE_ID_MIN_VALUE;
        }
        return nextChatMessageId;
    }
}
