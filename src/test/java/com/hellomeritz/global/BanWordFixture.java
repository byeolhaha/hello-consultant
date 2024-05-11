package com.hellomeritz.global;

import com.hellomeritz.chat.domain.BanWord;
import com.hellomeritz.chat.global.SourceLanguage;

public class BanWordFixture {

    public static BanWord banWordByChina() {
        return BanWord.to(
            "笨蛋",
            SourceLanguage.CHINA
        );
    }
}
