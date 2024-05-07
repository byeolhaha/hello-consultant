package com.hellomeritz.global;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BotRequest(
    @JsonProperty("chat_id")
    String chatId,
    String text
) {

    public static BotRequest to(
        String chatId,
        String text
    ) {
        return new BotRequest(
            chatId,
            text
        );
    }
}
