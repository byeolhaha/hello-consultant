package com.hello.chat.controller.dto.request;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.service.dto.param.ChatMessageSttParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttRequest(
    @Positive(message = "userId는 음수이거나 0일 수 없습니다.")
    @NotNull(message = "userId는 null일 수 없습니다.")
    Long userId,

    @NotNull(message = "isFC는 null일 수 없습니다.")
    Boolean isFC,

    @NotBlank(message = "sourceLang은 빈값일 수 없습니다.")
    String sourceLang
) {

    public ChatMessageSttParam toChatMessageSttParam(
        MultipartFile audioFile,
        long chatRoomId
    ) {
        return new ChatMessageSttParam(
            audioFile,
            userId,
            isFC,
            chatRoomId,
            SourceLanguage.findSourceLanguage(sourceLang)
        );
    }
}
