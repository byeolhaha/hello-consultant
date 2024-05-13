package com.hello.chat.global.stt.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SttWhisperResponse {

    @JsonProperty("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
