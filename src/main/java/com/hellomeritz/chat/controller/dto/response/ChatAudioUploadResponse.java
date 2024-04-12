package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatAudioUploadResult;

public record ChatAudioUploadResponse(
        String audioUrl,
        String createdAt
) {
    public static ChatAudioUploadResponse to(ChatAudioUploadResult result) {
        return new ChatAudioUploadResponse(
                result.audioUrl(),
                result.createdAt()
        );
    }
}
