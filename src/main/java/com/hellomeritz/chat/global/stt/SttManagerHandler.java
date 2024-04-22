package com.hellomeritz.chat.global.stt;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SttManagerHandler {

    private final Map<String, SttManager> sttManagers = new ConcurrentHashMap<>();

    public SttManagerHandler() {
        sttManagers.put(SttProvider.GOOGLE.name(), new SttGoogleManager());
        sttManagers.put(SttProvider.WHISPER.name(), new SttWhisperManager());
    }

    public SttManager getSttManager(String sttProviderName) {
        return sttManagers.get(sttProviderName);
    }
}
