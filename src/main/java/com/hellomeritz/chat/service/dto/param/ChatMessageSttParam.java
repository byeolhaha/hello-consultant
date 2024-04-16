package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.stt.SttRequest;
import com.hellomeritz.chat.global.uploader.AudioUploadResponse;
import org.springframework.web.multipart.MultipartFile;

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
