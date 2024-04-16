package com.hellomeritz.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TargetLanguage {
    중국("ZH"),
    미국("EN-US"),
    영국("EN-GB"),
    러시아("RU"),
    일본("JA"),
    한국("KO"),
    프랑스("FR"),
    스페인("ES"),
    이탈리아("IT");

    private String deeplLang;

    TargetLanguage(String deeplLang) {
        this.deeplLang = deeplLang;
    }

    public static boolean checkTranslatorFormat(String targetLang) {
        return Arrays.stream(TargetLanguage.values())
                .noneMatch(targetLanguage -> targetLanguage.deeplLang.equals(targetLang));
    }

    public static TargetLanguage findTranslatorTargetLanguage(String language) {
        return Arrays.stream(TargetLanguage.values())
                .filter(targetLanguage -> targetLanguage.deeplLang.equals(language))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 language가 없습니다."));
    }

}
