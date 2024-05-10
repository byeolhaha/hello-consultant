package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatRoomInfoOfConsultantResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomInfoOfConsultantResults;

import java.util.List;

public record ChatRoomInfoOfConsultantResponses(
    List<ChatRoomInfoOfConsultantResponse> responses
) {

    public static ChatRoomInfoOfConsultantResponses to(
        ChatRoomInfoOfConsultantResults results
    ) {
        return new ChatRoomInfoOfConsultantResponses(
            results.chatRoomInfoOfConsultantResults()
                .stream()
                .map(ChatRoomInfoOfConsultantResponse::to)
                .toList()
        );
    }

    public record ChatRoomInfoOfConsultantResponse(
        long chatRoomId,
        String contents,
        String messageCreatedAt,
        long notReadCount,
        String foreignerName,
        String chatRoomCreatedAt
    ) {
        public static ChatRoomInfoOfConsultantResponse to(
            ChatRoomInfoOfConsultantResult result
        ) {
            return new ChatRoomInfoOfConsultantResponse(
                result.chatRoomId(),
                result.contents(),
                result.messageCreatedAt().toString(),
                result.notReadCount(),
                result.foreignerName(),
                result.chatRoomCreatedAt().toString()
            );
        }
    }

}
