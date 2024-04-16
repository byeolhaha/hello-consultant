package com.hellomeritz.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SourceLanguage {

    중국("ZH", "zh"),
    미국("EN", "en-US"),
    영국("EN", "en-GB"),
    러시아("RU", "ru-RU"),
    일본("JA", "ja-JP"),
    한국("KO", "ko-KR"),
    프랑스("FR", "fr-BE"),
    스페인("ES", "eu-ES"),
    이탈리아("IT", "it-IT");

    private String deeplLang;
    private String googleSttLang;

    SourceLanguage(String deeplLang, String googleSttLang) {
        this.deeplLang = deeplLang;
        this.googleSttLang = googleSttLang;
    }

    public static boolean checkTranslatorFormat(String sourceLang) {
        return Arrays.stream(SourceLanguage.values())
                .noneMatch(sourceLanguage -> sourceLanguage.deeplLang.equals(sourceLang));
    }

    public static boolean checkSttFormat(String sourceLang) {
        return Arrays.stream(SourceLanguage.values())
                .noneMatch(sourceLanguage -> sourceLanguage.googleSttLang.equals(sourceLang));
    }
}
