package com.hellomeritz.chat.controller.dto.request;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.TargetLanguage;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import jakarta.validation.constraints.AssertTrue;
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

    @AssertTrue(message = "sourceLang이 enum 형식에 맞지 않습니다.")
    public boolean checkSourceLangFormat() {
        return SourceLanguage.checkTranslatorFormat(sourceLang);
    }

    @AssertTrue(message = "targetLang이 enum 형식에 맞지 않습니다.")
    public boolean checkTargetLangFormat() {
        return TargetLanguage.checkTranslatorFormat(targetLang);
    }

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
