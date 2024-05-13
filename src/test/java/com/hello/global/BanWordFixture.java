package com.hello.global;

import com.hello.chat.domain.BanWord;
import com.hello.chat.global.SourceLanguage;

public class BanWordFixture {

    public static BanWord banWordByChina() {
        return BanWord.to(
            "笨蛋",
            SourceLanguage.CHINA
        );
    }
}
