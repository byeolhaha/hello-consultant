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
    private static final String INITIAL_NEXT_CHAT_MESSAGE_ID = "";

    public static ChatMessageGetRepositoryResponses to(
        List<ChatMessage> chatMessages,
        int pageSize) {
        return new ChatMessageGetRepositoryResponses(
            chatMessages.stream()
                .sorted(Comparator.comparing(ChatMessage::getId))
                .toList(),
            getNextChatMessageId(chatMessages),
            hasNext(chatMessages.size(), pageSize)
        );
    }

    private static boolean hasNext(int returnSize, int pageSize) {
        return returnSize == pageSize;
    }

    private static String getNextChatMessageId(List<ChatMessage> chatMessages) {
        if (chatMessages.isEmpty()) {
            return INITIAL_NEXT_CHAT_MESSAGE_ID;
        }
        return chatMessages.get(chatMessages.size() - 1).getId();
    }
}
