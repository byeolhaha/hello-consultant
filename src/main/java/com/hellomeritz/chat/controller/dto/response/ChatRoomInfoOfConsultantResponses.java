package com.hellomeritz.chat.controller.dto.response;

import com.hellomeritz.chat.service.dto.result.ChatRoomInfoOfConsultantResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomInfoResults;

import java.util.List;

public record ChatRoomInfoResponses(
    List<ChatRoomInfoResponse> chatRoomInfoResponses
) {

    public static ChatRoomInfoResponses to(
        ChatRoomInfoResults results
    ) {
        return new ChatRoomInfoResponses(
            results.chatRoomInfoOfConsultantResults()
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
            ChatRoomInfoOfConsultantResult result
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
