package com.hello.chat.controller.dto.response;

import com.hello.chat.service.dto.result.ChatMessageTranslateResults;

import java.time.LocalDateTime;
import java.util.List;

public record ChatMessageTranslateResponses(
    List<ChatMessageTranslateResponse> chatMessageTranslateResponses
) {
    public static ChatMessageTranslateResponses to(ChatMessageTranslateResults results) {
        return new ChatMessageTranslateResponses(
            results.chatMessageTranslateResults()
                .stream()
                .map(ChatMessageTranslateResponse::to)
                .toList()
        );
    }

    record ChatMessageTranslateResponse(
        String chatMessageId,
        String contents,
        LocalDateTime createdAt,
        boolean isFC
    ){
        public static ChatMessageTranslateResponse to(
            ChatMessageTranslateResults.ChatMessageTranslateResult result
        ){
            return new ChatMessageTranslateResponse(
                result.chatMessageId(),
                result.contents(),
                result.createdAt(),
                result.isFC()
            );
        }

    }
}
