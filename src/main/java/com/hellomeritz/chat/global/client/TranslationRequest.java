package com.hellomeritz.chat.global.client;

public record TranslationRequest(
    String contents,
    String targetLang,
    String sourceLang
) {
}
