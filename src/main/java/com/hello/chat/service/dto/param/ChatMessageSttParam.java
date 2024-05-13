package com.hello.chat.service.dto.param;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.stt.dto.SttRequest;
import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttParam(
    MultipartFile audioFile,
    long userId,
    boolean isFC,
    long chatRoomId,
    SourceLanguage sourceLang
) {

    public SttRequest toSttRequest(String audioUrl) {
        return new SttRequest(audioFile, audioUrl, sourceLang);
    }

    public SttRequest toEmptySttRequest() {
        return new SttRequest(audioFile, "", sourceLang);
    }

    public ChatAudioUploadParam toChatAudioUploadParam() {
        return new ChatAudioUploadParam(
            audioFile,
            userId,
            isFC,
            chatRoomId
        );
    }
}
