package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;

import java.util.Comparator;
import java.util.List;

public record ChatMessageGetResults(
    List<ChatMessageGetResult> chatMessages,
    String nextChatMessageId
) {

    public static ChatMessageGetResults to(List<ChatMessage> chatMessages, long myId) {
        return new ChatMessageGetResults(
            chatMessages.stream()
                .sorted(Comparator.comparing(ChatMessage::getId))
                .map(chatMessage -> new ChatMessageGetResult(
                    chatMessage.getContents(),
                    chatMessage.getCreatedAt().toString(),
                    chatMessage.getUserId() == myId
                )).toList(),
            chatMessages.get(chatMessages.size()-1).getId()
        );
    }

    public record ChatMessageGetResult(
        String contents,
        String createdAt,
        boolean isMine
    ) {

    }
}
