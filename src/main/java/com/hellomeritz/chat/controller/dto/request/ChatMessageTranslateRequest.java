package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ChatMessageTranslateRequest(
        @NotBlank(message = "contents는 빈값일 수 없습니다.")
        String contents,

        @Positive(message = "userId는 양수여야 합니다.")
        long userId,
        boolean isFC,

        @NotBlank(message = "targetLang는 빈값일 수 없습니다.")
        String targetLang,

        @NotBlank(message = "sourceLang는 빈값일 수 없습니다.")
        String sourceLang
) {
    public ChatMessageTextParam toChatMessageTextParam(long chatRoomId) {
        return new ChatMessageTextParam(
                contents,
                userId,
                isFC,
                chatRoomId,
                targetLang,
                sourceLang
        );
    }
}
