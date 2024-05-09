package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatRoomInfoResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomInfoResults;

import java.time.LocalDateTime;
import java.util.List;

public record ChatRoomInfoResponses(
    List<ChatRoomInfoResponse> chatRoomInfoResponses
) {

    public static ChatRoomInfoResponses to(
        ChatRoomInfoResults results
    ) {
        return new ChatRoomInfoResponses(
            results.chatRoomInfoResults()
                .stream()
                .map(ChatRoomInfoResponse::to)
                .toList()
        );
    }

    public record ChatRoomInfoResponse(
        long chatRoomId,
        String contents,
        String createdAt,
        long notReadCount
    ) {
        public static ChatRoomInfoResponse to(
            ChatRoomInfoResult result
        ) {
            return new ChatRoomInfoResponse(
                result.chatRoomId(),
                result.contents(),
                result.createdAt().toString(),
                result.notReadCount()
            );
        }
    }

}
