package com.hellomeritz.chat.global.stt;

import com.hellomeritz.chat.global.uploader.AudioUploadResponse;

public interface SttManager {

    SttResponse asyncRecognizeGcs(SttRequest request);
}
