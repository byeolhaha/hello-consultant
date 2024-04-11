package com.hellomeritz.chat.repository.chatmessage.dto;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;

import java.util.Comparator;
import java.util.List;

public record ChatMessageGetRepositoryResponses(
        List<ChatMessage> chatMessages,
        String nextChatMessageId,
        boolean hasNext
) {

    public static ChatMessageGetRepositoryResponses to(
            List<ChatMessage> chatMessages,
            int pageSize) {
        return new ChatMessageGetRepositoryResponses(
                chatMessages.stream()
                        .sorted(Comparator.comparing(ChatMessage::getId))
                        .toList(),
                chatMessages.get(chatMessages.size() - 1).getId(),
                hasNext(chatMessages.size(), pageSize)

        );
    }

    private static boolean hasNext(int returnSize, int pageSize) {
        return returnSize == pageSize;
    }
}
