package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;

public record ChatMessageTranslateRequest(
    String contents,
    long userId,
    boolean isFC,
    long chatRoomId,
    String targetLang,
    String sourceLang
) {
    public ChatMessageTextParam toChatMessageTextParam() {
        return new ChatMessageTextParam(
            contents,
            userId,
            isFC,
            chatRoomId,
            targetLang,
            sourceLang
        );
    }
}
