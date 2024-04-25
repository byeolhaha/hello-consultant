package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatMessageType;
import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.TargetLanguage;
import com.hellomeritz.chat.global.translator.TranslationRequest;

public record ChatMessageTextParam(
    String contents,
    long userId,
    boolean isFC,
    long chatRoomId,
    TargetLanguage targetLang,
    SourceLanguage sourceLang
) {
    public ChatMessage toChatMessage() {
        return ChatMessage.of(
            contents,
            ChatMessageType.TEXT.name(),
            userId,
            isFC,
            chatRoomId
        );
    }

    public ChatMessage toChatMessage(String translatedContents) {
        return ChatMessage.of(
            translatedContents,
            ChatMessageType.TRANSLATED_TEXT.name(),
            userId,
            isFC,
            chatRoomId
        );
    }

    public TranslationRequest toTranslationRequest() {
        return new TranslationRequest(
            contents.replace("오디오",""),
            targetLang,
            sourceLang
        );
    }
}
