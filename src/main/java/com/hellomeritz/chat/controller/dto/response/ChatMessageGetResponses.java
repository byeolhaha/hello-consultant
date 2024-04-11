package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;

import java.util.List;

public record ChatMessageGetResponses(
    List<ChatMessageGetResponse> chatMessages,
    String nextChatMessageId

) {
    public static ChatMessageGetResponses to(ChatMessageGetResults results) {
        return new ChatMessageGetResponses(
            results.chatMessages().stream()
                .map(chatMessageGetResult -> new ChatMessageGetResponse(
                    chatMessageGetResult.contents(),
                    chatMessageGetResult.createdAt(),
                    chatMessageGetResult.isMine()
                )).toList(),
            results.nextChatMessageId()
        );
    }

    public record ChatMessageGetResponse(
        String contents,
        String createdAt,
        boolean isMine
    ) {

    }
}
