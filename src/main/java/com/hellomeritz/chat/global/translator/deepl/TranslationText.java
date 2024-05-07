package com.hellomeritz.chat.global.translator.deepl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TranslationText(
    String detected_source_language,
    String text
) {
}
