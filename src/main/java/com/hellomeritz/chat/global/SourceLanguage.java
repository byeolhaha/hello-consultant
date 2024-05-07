package com.hellomeritz.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SourceLanguage {

    CHINA("ZH", "zh", "zh"),
    US("EN", "en-US", "en"),
    UK("EN", "en-GB", "en"),
    RUSSIAN("RU", "ru-RU", "ru"),
    JAPANESE("JA", "ja-JP", "ja"),
    KOREAN("KO", "ko-KR", "ko"),
    FRENCH("FR", "fr-BE", "fr"),
    SPANISH("ES", "eu-ES", "es"),
    ITALIAN("IT", "it-IT", "it");

    private String deeplLang;
    private String googleSttLang;
    private String googleTranslateLang;

    SourceLanguage(String deeplLang, String googleSttLang, String googleTranslateLang) {
        this.deeplLang = deeplLang;
        this.googleSttLang = googleSttLang;
        this.googleTranslateLang = googleTranslateLang;
    }

    public static boolean checkFormat(String sourceLang) {
        return Arrays.stream(SourceLanguage.values())
            .noneMatch(sourceLanguage -> sourceLanguage.name().equals(sourceLang));
    }

    public static SourceLanguage findSourceLanguage(String language) {
        return Arrays.stream(SourceLanguage.values())
            .filter(sourceLanguage -> sourceLanguage.name().equals(language))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당하는 language가 없습니다."));
    }

}
