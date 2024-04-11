package com.hellomeritz.chat.controller.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttRequest(
        MultipartFile contents,
        long userId,
        boolean isFC,
        long chatRoomId,
        String sourceLang
) {
}
