package com.hellomeritz.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TargetLanguage {
    CHINA("ZH"),
    US("EN-US"),
    UK("EN-GB"),
    RUSSIAN("RU"),
    JAPANESE("JA"),
    KOREAN("KO"),
    FRENCH("FR"),
    SPANISH("ES"),
    ITALIAN("IT");

    private String deeplLang;

    TargetLanguage(String deeplLang) {
        this.deeplLang = deeplLang;
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
