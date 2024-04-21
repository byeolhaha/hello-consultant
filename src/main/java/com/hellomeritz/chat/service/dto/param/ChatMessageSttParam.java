package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.stt.SttRequest;

public record ChatMessageSttParam(
        String audioUrl,
        long userId,
        boolean isFC,
        long chatRoomId,
        SourceLanguage sourceLang
) {

    public SttRequest toSttRequest() {
        return new SttRequest(audioUrl, sourceLang);
    }
}
