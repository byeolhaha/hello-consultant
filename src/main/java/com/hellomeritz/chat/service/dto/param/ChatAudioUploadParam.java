package com.hellomeritz.chat.service.dto.param;

import org.springframework.web.multipart.MultipartFile;

public record ChatAudioUploadParam(
        MultipartFile audioFile,
        long userId,
        boolean isFC,
        long chatRoomId
) {
}
