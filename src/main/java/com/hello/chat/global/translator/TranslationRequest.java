package com.hello.chat.global.translator;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.TargetLanguage;

public record TranslationRequest(
    String contents,
    TargetLanguage targetLang,
    SourceLanguage sourceLang
) {
}
