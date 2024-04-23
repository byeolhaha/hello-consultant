package com.hellomeritz.chat.global.stt;

import com.hellomeritz.chat.global.stt.dto.SttRequest;
import com.hellomeritz.chat.global.stt.dto.SttResponse;

public interface SttManager {

    SttResponse asyncRecognizeAudio (SttRequest request);
}
