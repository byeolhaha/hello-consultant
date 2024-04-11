package com.hellomeritz.chat.global.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TranslationResponse(
    List<TranslationText> translations
) {

    public String getText() {
        return translations.stream()
            .map(TranslationText::text)
            .collect(Collectors.joining("\n"));
    }
}
