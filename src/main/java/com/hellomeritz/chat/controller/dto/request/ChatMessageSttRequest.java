package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatMessageSttRequest(

        @NotBlank(message = "audioUrl은 빈값일 수 없습니다.")
        String audioUrl,

        @Positive(message = "userId는 양수여야 합니다.")
        @NotNull(message = "userId는 null일 수 없습니다.")
        Long userId,

        @NotNull(message = "isFC는 null일 수 없습니다.")
        Boolean isFC,

        @NotBlank(message = "sourceLang은 빈값일 수 없습니다.")
        String sourceLang
) {

    @AssertTrue(message = "sourceLang 형식이 enum 형식에 맞지 않습니다.")
    public boolean checkSourceLangFormat() {
        return SourceLanguage.checkFormat(sourceLang);
    }

    public ChatMessageSttParam toChatMessageSttParam(long chatRoomId) {
        return new ChatMessageSttParam(
                audioUrl,
                userId,
                isFC,
                chatRoomId,
                SourceLanguage.findSourceLanguage(sourceLang)
        );
    }
}
