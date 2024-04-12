package com.hellomeritz.chat.service.dto.param;

import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttParam(
    MultipartFile audioFile,
    long userId,
    boolean isFC,
    long chatRoomId,
    String sourceLang
) {
}
