package com.hello.chat.service.dto.result;

import com.hello.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;

import java.util.List;

public record ChatMessageGetResults(
    List<ChatMessageGetResult> chatMessages,
    String nextChatMessageId,
    boolean hasNext
) {

    public static ChatMessageGetResults to(
        ChatMessageGetRepositoryResponses chatMessages) {
        return new ChatMessageGetResults(
            chatMessages.chatMessages().stream()
                .map(chatMessage -> new ChatMessageGetResult(
                    chatMessage.getId(),
                    chatMessage.getContents(),
                    chatMessage.getCreatedAt().toString(),
                    chatMessage.getIsFC(),
                    chatMessage.getReadOrNot()
                )).toList(),
            chatMessages.nextChatMessageId(),
            chatMessages.hasNext()
        );
    }

    public record ChatMessageGetResult(
        String chatMessageId,
        String contents,
        String createdAt,
        boolean isFC,
        boolean readOrNot
    ) {

    }

}
