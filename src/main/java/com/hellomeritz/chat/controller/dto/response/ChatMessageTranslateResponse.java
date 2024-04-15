package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateResult;

public record ChatMessageTranslateResponse(
    String originContents,
    String translatedContents,
    String createdAt
) {
    public static ChatMessageTranslateResponse to(ChatMessageTranslateResult result) {
        return new ChatMessageTranslateResponse(
            result.originContents(),
            result.translatedContents(),
            result.createdAt().toString()
        );
    }
}
