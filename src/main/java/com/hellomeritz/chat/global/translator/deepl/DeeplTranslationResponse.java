package com.hellomeritz.chat.global.translator.deepl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeeplTranslationResponse(
    List<TranslationText> translations
) {

    public String getText() {
        return translations.stream()
            .map(TranslationText::text)
            .collect(Collectors.joining("\n"));
    }
}
