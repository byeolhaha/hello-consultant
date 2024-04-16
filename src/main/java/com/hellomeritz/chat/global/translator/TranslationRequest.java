package com.hellomeritz.chat.global.translator;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.TargetLanguage;

public record TranslationRequest(
    String contents,
    TargetLanguage targetLang,
    SourceLanguage sourceLang
) {
}
