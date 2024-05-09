package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;

import java.util.List;

public record ChatMessageGetResponses(
    List<ChatMessageGetResponse> chatMessages,
    String nextChatMessageId,
    boolean hasNext

) {
    public static ChatMessageGetResponses to(ChatMessageGetResults results) {
        return new ChatMessageGetResponses(
            results.chatMessages().stream()
                .map(chatMessageGetResult -> new ChatMessageGetResponse(
                    chatMessageGetResult.chatMessageId(),
                    chatMessageGetResult.contents(),
                    chatMessageGetResult.createdAt(),
                    chatMessageGetResult.isFC(),
                    chatMessageGetResult.readOrNot()
                )).toList(),
            results.nextChatMessageId(),
            results.hasNext()
        );
    }

    public record ChatMessageGetResponse(
        String chatMessageId,
        String contents,
        String createdAt,
        boolean isFC,
        boolean readOrNot
    ) {

    }
}
