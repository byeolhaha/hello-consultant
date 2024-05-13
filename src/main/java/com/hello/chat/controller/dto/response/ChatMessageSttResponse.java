package com.hello.chat.controller.dto.response;

import com.hello.chat.service.dto.result.ChatMessageSttResult;

public record ChatMessageSttResponse(
    String textBySpeech,
    String createdAt,
    String sttProvider
) {
    public static ChatMessageSttResponse to(ChatMessageSttResult result) {
        return new ChatMessageSttResponse(
            result.textBySpeech(),
            result.createdAt(),
            result.sttProvider()
        );
    }
}
