package com.hellomeritz.chat.service.dto.result;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import com.hellomeritz.chat.service.dto.param.ChatMessageGetParam;

import java.util.Comparator;
import java.util.List;

public record ChatMessageGetResults(
        List<ChatMessageGetResult> chatMessages,
        String nextChatMessageId,
        boolean hasNext
) {

    public static ChatMessageGetResults to(
            ChatMessageGetRepositoryResponses chatMessages,
            ChatMessageGetParam param) {
        return new ChatMessageGetResults(
                chatMessages.chatMessages().stream()
                        .map(chatMessage -> new ChatMessageGetResult(
                                chatMessage.getId(),
                                chatMessage.getContents(),
                                chatMessage.getCreatedAt().toString(),
                                isMine(chatMessage, param)
                        )).toList(),
                chatMessages.nextChatMessageId(),
                chatMessages.hasNext()
        );
    }

    public record ChatMessageGetResult(
            String chatMessageId,
            String contents,
            String createdAt,
            boolean isMine
    ) {

    }

    private static boolean isMine(ChatMessage chatMessage, ChatMessageGetParam param) {
        return chatMessage.getUserId() == param.myId()
                && chatMessage.isFC() == param.isFC();
    }
}
