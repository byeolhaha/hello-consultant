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
    private static final int NEXT_ID_INDEX = 0;

    public static ChatMessageGetRepositoryResponses to(
        List<ChatMessage> chatMessages,
        boolean hasNext) {
        List<ChatMessage> sortedMessages = chatMessages.stream()
            .sorted(Comparator.comparing(ChatMessage::getId))
            .toList();
        return new ChatMessageGetRepositoryResponses(
            sortedMessages,
            getNextChatMessageId(sortedMessages),
            hasNext
        );
    }

    private static String getNextChatMessageId(List<ChatMessage> chatMessages) {
        if (chatMessages.isEmpty()) {
            return INITIAL_NEXT_CHAT_MESSAGE_ID;
        }
        return chatMessages.get(NEXT_ID_INDEX).getId();
    }
}
