package com.hello.chat.global.stt;

import com.hello.chat.global.stt.google.SttGoogleManager;
import com.hello.chat.global.stt.openai.SttWhisperManager;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SttManagerHandler {

    private final SttGoogleManager sttGoogleManager;
    private final SttWhisperManager sttWhisperManager;
    private final Map<String, SttManager> sttManagers = new ConcurrentHashMap<>();

    public SttManagerHandler(
        SttGoogleManager sttGoogleManager, SttWhisperManager sttWhisperManager) {
        this.sttGoogleManager = sttGoogleManager;
        this.sttWhisperManager = sttWhisperManager;
        sttManagers.put(SttProvider.GOOGLE.name(), sttGoogleManager);
        sttManagers.put(SttProvider.WHISPER.name(), sttWhisperManager);
    }

    public SttManager getSttManager(String sttProviderName) {
        return sttManagers.get(sttProviderName);
    }
}
