package com.hellomeritz.chat.global.uploader;

public record AudioUploadResponse(
    String audioUrl
) {

    public static AudioUploadResponse to(String audioUrl) {
        return new AudioUploadResponse(audioUrl);
    }
}
