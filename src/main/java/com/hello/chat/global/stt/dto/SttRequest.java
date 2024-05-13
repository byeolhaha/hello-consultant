package com.hello.chat.global.stt.dto;

import com.hello.chat.global.SourceLanguage;
import org.springframework.web.multipart.MultipartFile;

public record SttRequest(
    MultipartFile audioFile,
    String audioUrl,
    SourceLanguage sourceLang
) {
}
