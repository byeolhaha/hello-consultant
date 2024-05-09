package com.hellomeritz.chat.service.dto.result;

import java.util.List;

public record ChatRoomInfoResults(
    List<ChatRoomInfoResult> chatRoomInfoResults
) {
    public static ChatRoomInfoResults to(
        List<ChatRoomInfoResult> chatRoomInfoResults
    ){
        return new ChatRoomInfoResults(
            chatRoomInfoResults
        );
    }
}
