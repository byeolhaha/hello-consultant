package com.hello.chat.global.uploader;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.domain.ChatMessageType;
import com.hello.chat.service.dto.param.ChatAudioUploadParam;

public record AudioUploadResponse(
    String audioUrl
) {

    public static AudioUploadResponse to(String audioUrl) {
        return new AudioUploadResponse(audioUrl);
    }

    public ChatMessage toChatMessage(ChatAudioUploadParam param) {
        return ChatMessage.of(
            audioUrl,
            ChatMessageType.AUDIO.name(),
            param.userId(),
            param.isFC(),
            param.chatRoomId(),
            false
        );
    }
}
