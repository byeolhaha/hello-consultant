package com.hellomeritz.chat.global.stt;

import com.hellomeritz.chat.global.stt.google.SttGoogleManager;
import com.hellomeritz.chat.global.stt.openai.SttWhisperManager;
import com.hellomeritz.chat.global.stt.openai.client.OpenAIClient;
import com.hellomeritz.chat.global.stt.openai.client.OpenAIClientConfig;
import com.hellomeritz.chat.global.uploader.AudioUploader;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SttManagerHandler {

    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;

    private final Map<String, SttManager> sttManagers = new ConcurrentHashMap<>();

    public SttManagerHandler(
        OpenAIClient openAIClient, OpenAIClientConfig openAIClientConfig) {
        this.openAIClient = openAIClient;
        this.openAIClientConfig = openAIClientConfig;
        sttManagers.put(SttProvider.GOOGLE.name(), new SttGoogleManager());
        sttManagers.put(SttProvider.WHISPER.name(), new SttWhisperManager(openAIClient, openAIClientConfig));
    }

    public SttManager getSttManager(String sttProviderName) {
        return sttManagers.get(sttProviderName);
    }
}
