package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record ChatMessageSttRequest(

        @NotBlank(message = "audioUrl은 빈값일 수 없습니다.")
        String audioUrl,

        @Positive(message = "userId는 양수여야 합니다.")
        long userId,
        boolean isFC,

        @NotBlank(message = "sourceLang은 빈값일 수 없습니다.")
        String sourceLang
) {
    public ChatMessageSttParam toChatMessageSttParam(long chatRoomId) {
        return new ChatMessageSttParam(
                audioUrl,
                userId,
                isFC,
                chatRoomId,
                sourceLang
        );
    }
}
