package com.hellomeritz.chat.global.translator;

public record TranslationRequest(
    String contents,
    String targetLang,
    String sourceLang
) {
}
