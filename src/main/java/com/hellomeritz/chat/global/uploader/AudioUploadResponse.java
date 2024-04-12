package com.hellomeritz.chat.global.uploader;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatMessageType;
import com.hellomeritz.chat.service.dto.param.ChatAudioUploadParam;
import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;

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
            param.chatRoomId()
        );
    }
}
