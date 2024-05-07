package com.hellomeritz.chat.global.translator;

public record TranslationResponse(
    String translatedText
) {
    public static TranslationResponse to(String translatedText) {
        return new TranslationResponse(translatedText);
    }
}
