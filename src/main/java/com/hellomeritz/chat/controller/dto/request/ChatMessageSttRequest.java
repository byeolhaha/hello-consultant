package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;
import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttRequest(
        String audioUrl,
        long userId,
        boolean isFC,
        String sourceLang
) {
    public ChatMessageSttParam toChatMessageSttParam(long chatRoomId) {
        return new ChatMessageSttParam(
                audioUrl,
                userId,
                isFC,
                chatRoomId,
                sourceLang
        );
    }
}
