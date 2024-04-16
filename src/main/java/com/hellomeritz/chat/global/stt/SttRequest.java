package com.hellomeritz.chat.global.stt;

import com.hellomeritz.chat.global.SourceLanguage;

public record SttRequest(
        String audioUrl,
        SourceLanguage sourceLang
) {
}
