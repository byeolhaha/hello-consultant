package com.hellomeritz.chat.global.stt;

public record SttRequest(
        String audioUrl,
        String sourceLang
) {
}
