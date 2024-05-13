package com.hello.chat.global.stt;

import com.hello.chat.global.stt.dto.SttRequest;
import com.hello.chat.global.stt.dto.SttResponse;

public interface SttManager {

    SttResponse asyncRecognizeAudio (SttRequest request);
}
