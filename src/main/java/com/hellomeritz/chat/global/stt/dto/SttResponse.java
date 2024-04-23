package com.hellomeritz.chat.global.stt;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatMessageType;
import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;

public record SttResponse(
    String textBySpeech
) {
    public ChatMessage toChatMessage(ChatMessageSttParam param) {
        return ChatMessage.of(
            textBySpeech,
            ChatMessageType.TEXT.name(),
            param.userId(),
            param.isFC(),
            param.chatRoomId()
        );
    }

    public static SttResponse emptySttResponse() {
        return new SttResponse("");
    }

    public static SttResponse to(String textBySpeech) {
        return new SttResponse(textBySpeech);
    }
}
