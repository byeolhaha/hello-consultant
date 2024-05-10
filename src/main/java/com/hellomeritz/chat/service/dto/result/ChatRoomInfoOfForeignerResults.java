package com.hellomeritz.chat.service.dto.result;

import java.util.Comparator;
import java.util.List;

public record ChatRoomInfoOfForeignerResults(
    List<ChatRoomInfoOfForeignerResult> chatRoomInfoOfConsultantResults
) {
    public static ChatRoomInfoOfForeignerResults to(
        List<ChatRoomInfoOfForeignerResult> results
    ){
        return new ChatRoomInfoOfForeignerResults(
            results
                .stream()
                .sorted(Comparator.comparing(ChatRoomInfoOfForeignerResult::messageCreatedAt).reversed())
                .toList()
        );
    }
}
