package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;
import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttRequest(
        long userId,
        boolean isFC,
        long chatRoomId,
        String sourceLang
) {
    public ChatMessageSttParam toChatMessageSttParam(MultipartFile file) {
        return new ChatMessageSttParam(
            file,
            userId,
            isFC,
            chatRoomId,
            sourceLang
        );
    }
}
