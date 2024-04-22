package com.hellomeritz.chat.global.stt;

import java.io.IOException;

public interface SttManager {

    SttResponse asyncRecognizeAudio (SttRequest request);
}
