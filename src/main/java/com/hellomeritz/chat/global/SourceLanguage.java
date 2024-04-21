package com.hellomeritz.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SourceLanguage {

    CHINA("ZH", "zh"),
    US("EN", "en-US"),
    UK("EN", "en-GB"),
    RUSSIAN("RU", "ru-RU"),
    JAPANESE("JA", "ja-JP"),
    KOREAN("KO", "ko-KR"),
    FRENCH("FR", "fr-BE"),
    SPANISH("ES", "eu-ES"),
    ITALIAN("IT", "it-IT");

    private String deeplLang;
    private String googleSttLang;

    SourceLanguage(String deeplLang, String googleSttLang) {
        this.deeplLang = deeplLang;
        this.googleSttLang = googleSttLang;
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
