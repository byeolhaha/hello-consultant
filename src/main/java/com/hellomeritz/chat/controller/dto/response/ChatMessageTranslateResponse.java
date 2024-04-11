package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;

public record ChatMessageTranslateResponse(
    String contents,
    String translatedContents,
    String createdAt
) {
    public static ChatMessageTranslateResponse to(ChatMessageTranslateTextResult result) {
        return new ChatMessageTranslateResponse(
            result.originContents(),
            result.translatedContents(),
            result.createdAt().toString()
        );
    }
}
