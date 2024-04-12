package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatMessageSttResult;

public record ChatMessageSttResponse(
        String audioUrl,
        String speechToText,
        String createdAt
) {
    public static ChatMessageSttResponse to(ChatMessageSttResult result) {
        return new ChatMessageSttResponse(
                result.audioUrl(),
                result.speechToText(),
                result.createdAt());
    }
}
