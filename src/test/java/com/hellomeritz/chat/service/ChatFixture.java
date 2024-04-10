package com.hellomeritz.chat.service;

import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;

public class ChatFixture {

    private ChatFixture() {
        throw new AssertionError("생성자를 통해서 인스턴스를 생성할 수 없습니다.");
    }

    public static ChatMessageTextParam chatMessageTextParam() {
        return new ChatMessageTextParam(
            "안녕하세요 반가워요",
            1,
            true,
            1,
            "EN",
            "KO"
        );
    }
}
