package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatAudioUploadParam;
import org.springframework.web.multipart.MultipartFile;

public record ChatAudioUploadRequest(
        long userId,
        boolean isFC
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
