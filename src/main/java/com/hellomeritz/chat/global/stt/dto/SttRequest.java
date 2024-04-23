package com.hellomeritz.chat.global.stt;

import com.hellomeritz.chat.global.SourceLanguage;
import org.springframework.web.multipart.MultipartFile;

public record SttRequest(
    MultipartFile audioFile,
    String audioUrl,
    SourceLanguage sourceLang
) {
}
