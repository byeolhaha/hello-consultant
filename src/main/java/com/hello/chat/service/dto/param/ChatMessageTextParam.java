package com.hello.chat.service.dto.param;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.domain.ChatMessageType;
import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.TargetLanguage;
import com.hello.chat.global.translator.TranslationRequest;

public record ChatMessageTextParam(
    String contents,
    long userId,
    boolean isFC,
    long chatRoomId,
    TargetLanguage targetLang,
    SourceLanguage sourceLang
) {
    public ChatMessage toReadChatMessage() {
        return ChatMessage.of(
            contents,
            ChatMessageType.TEXT.name(),
            userId,
            isFC,
            chatRoomId,
            true
        );
    }

    public ChatMessage toReadChatMessage(String translatedContents) {
        return ChatMessage.of(
            translatedContents,
            ChatMessageType.TRANSLATED_TEXT.name(),
            userId,
            isFC,
            chatRoomId,
            true
        );
    }

    public ChatMessage toNotReadChatMessage() {
        return ChatMessage.of(
            contents,
            ChatMessageType.TEXT.name(),
            userId,
            isFC,
            chatRoomId,
            false
        );
    }

    public ChatMessage toNotReadChatMessage(String translatedContents) {
        return ChatMessage.of(
            translatedContents,
            ChatMessageType.TRANSLATED_TEXT.name(),
            userId,
            isFC,
            chatRoomId,
            false
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
