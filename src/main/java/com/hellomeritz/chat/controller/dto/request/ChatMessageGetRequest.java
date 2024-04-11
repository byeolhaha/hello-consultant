package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatMessageGetParam;

import java.util.Objects;

public record ChatMessageGetRequest(
    long myId,
    String nextChatMessageId,
    long chatRoomId
) {
    private static final String CHAT_MESSAGE_ID_MIN_VALUE = "000000000000000000000000";

    public ChatMessageGetParam toChatMessageGetParam() {
        return new ChatMessageGetParam(
            myId,
            isFirstPage(),
            chatRoomId
        );
    }

    private String isFirstPage() {
        if (Objects.isNull(nextChatMessageId) || nextChatMessageId.isEmpty()) {
            return CHAT_MESSAGE_ID_MIN_VALUE;
        }
        return nextChatMessageId;
    }
}
