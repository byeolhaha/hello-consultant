package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatAudioUploadParam;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record ChatAudioUploadRequest(
        @Positive(message = "userId는 음수이거나 0일 수 없습니다.")
        long userId,
        Boolean isFC
) {

    public ChatAudioUploadParam toChatAudioUploadParam(
            MultipartFile audioFile,
            long chatRoomId
    ){
        return new ChatAudioUploadParam(
                audioFile,
                userId,
                isFC,
                chatRoomId
        );
    }
}
