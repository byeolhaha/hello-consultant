package com.hello.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TargetLanguage {
    CHINA("ZH", "zh"),
    US("EN-US", "en"),
    UK("EN-GB", "en"),
    RUSSIAN("RU", "ru"),
    JAPANESE("JA", "ja"),
    KOREAN("KO", "ko"),
    FRENCH("FR", "fr"),
    SPANISH("ES", "es"),
    ITALIAN("IT", "it");

    private String deeplLang;

    private String googleTranslateLang;

    TargetLanguage(String deeplLang, String googleTranslateLang) {
        this.deeplLang = deeplLang;
        this.googleTranslateLang = googleTranslateLang;
    }

    public static boolean checkFormat(String targetLang) {
        return Arrays.stream(TargetLanguage.values())
            .noneMatch(targetLanguage -> targetLanguage.name().equals(targetLang));
    }

    public static TargetLanguage findTargetLanguage(String language) {
        return Arrays.stream(TargetLanguage.values())
            .filter(targetLanguage -> targetLanguage.name().equals(language))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당하는 language가 없습니다."));
    }

}
