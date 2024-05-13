package com.hello.chat.controller.dto.response;

import com.hello.chat.service.dto.result.ChatAudioUploadResult;

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
