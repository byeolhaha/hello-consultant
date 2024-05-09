package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ChatMessageTranslateResults(
    List<ChatMessageTranslateResult> chatMessageTranslateResults
) {

    public static ChatMessageTranslateResults to(
        ChatMessage chatMessage,
        ChatMessage translatedChatMessage) {

        List<ChatMessageTranslateResult> chatMessageTranslateResults = new ArrayList<>();
        chatMessageTranslateResults.add(
            ChatMessageTranslateResult.to(
                chatMessage.getId(),
                chatMessage.getContents(),
                chatMessage.getIsFC(),
                chatMessage.getCreatedAt()));
        chatMessageTranslateResults.add(
            ChatMessageTranslateResult.to(
                translatedChatMessage.getId(),
                translatedChatMessage.getContents(),
                translatedChatMessage.getIsFC(),
                translatedChatMessage.getCreatedAt()));

        return new ChatMessageTranslateResults(
            chatMessageTranslateResults
        );
    }

    public record ChatMessageTranslateResult(
        String chatMessageId,

        String contents,
        boolean isFC,
        LocalDateTime createdAt
    ) {
        public static ChatMessageTranslateResult to(
            String chatMessageId,
            String contents,
            boolean isFC,
            LocalDateTime createdAt
        ) {
            return new ChatMessageTranslateResult(
                chatMessageId,
                contents,
                isFC,
                createdAt
            );
        }

    }

}
