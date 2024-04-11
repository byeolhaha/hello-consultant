package com.hellomeritz.chat.global.audio;

public record AudioUploadResponse(
        String audioUrl
) {

    public static AudioUploadResponse to(String audioUrl) {
        return new AudioUploadResponse(
                audioUrl
        );
    }
}
