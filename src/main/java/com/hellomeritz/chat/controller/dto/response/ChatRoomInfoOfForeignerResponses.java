package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatRoomInfoOfForeignerResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomInfoOfForeignerResults;

import java.util.List;

public record ChatRoomInfoOfForeignerResponses(
    List<ChatRoomInfoOfForeignerResponse> responses
) {
    public static ChatRoomInfoOfForeignerResponses to(
        ChatRoomInfoOfForeignerResults results
    ) {
        return new ChatRoomInfoOfForeignerResponses(
            results.chatRoomInfoOfConsultantResults()
                .stream()
                .map(ChatRoomInfoOfForeignerResponse::to)
                .toList()
        );
    }
    public record ChatRoomInfoOfForeignerResponse(
        long chatRoomId,
        String contents,
        String messageCreatedAt,
        long notReadCount,
        String consultantName,
        String profileUrl
    ) {
        public static ChatRoomInfoOfForeignerResponse to(
            ChatRoomInfoOfForeignerResult result
        ) {
            return new ChatRoomInfoOfForeignerResponse(
                result.chatRoomId(),
                result.contents(),
                result.messageCreatedAt().toString(),
                result.notReadCount(),
                result.consultantName(),
                result.profileUrl()
            );
        }

    }
}
