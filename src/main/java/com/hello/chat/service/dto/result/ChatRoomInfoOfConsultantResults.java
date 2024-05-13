package com.hello.chat.service.dto.result;

import java.util.Comparator;
import java.util.List;

public record ChatRoomInfoOfConsultantResults(
    List<ChatRoomInfoOfConsultantResult> chatRoomInfoOfConsultantResults
) {
    public static ChatRoomInfoOfConsultantResults to(
        List<ChatRoomInfoOfConsultantResult> chatRoomInfoOfConsultantResults
    ) {
        return new ChatRoomInfoOfConsultantResults(
            chatRoomInfoOfConsultantResults
                .stream()
                .sorted(Comparator.comparing(ChatRoomInfoOfConsultantResult::chatRoomCreatedAt).reversed())
                .toList()
        );
    }
}
