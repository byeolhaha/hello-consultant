package com.hello.chat.global.stt.dto;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.domain.ChatMessageType;
import com.hello.chat.service.dto.param.ChatMessageSttParam;

public record SttResponse(
    String textBySpeech
) {
    public ChatMessage toChatMessage(ChatMessageSttParam param) {
        return ChatMessage.of(
            textBySpeech,
            ChatMessageType.TEXT.name(),
            param.userId(),
            param.isFC(),
            param.chatRoomId(),
            false
        );
    }

    public static SttResponse emptySttResponse() {
        return new SttResponse("");
    }

    public static SttResponse to(String textBySpeech) {
        return new SttResponse(textBySpeech);
    }
}
