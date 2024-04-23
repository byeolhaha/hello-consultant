package com.hellomeritz.chat.global.stt.openai;


import com.hellomeritz.chat.global.stt.SttManager;
import com.hellomeritz.chat.global.stt.dto.SttRequest;
import com.hellomeritz.chat.global.stt.dto.SttResponse;
import com.hellomeritz.chat.global.stt.openai.client.OpenAIClient;
import com.hellomeritz.chat.global.stt.openai.client.OpenAIClientConfig;
import com.hellomeritz.chat.global.stt.openai.dto.WhisperTranscriptionRequest;
import com.hellomeritz.chat.global.stt.openai.dto.WhisperTranscriptionResponse;

public class SttWhisperManager implements SttManager {

    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;

    public SttWhisperManager(OpenAIClient openAIClient, OpenAIClientConfig openAIClientConfig) {
        this.openAIClient = openAIClient;
        this.openAIClientConfig = openAIClientConfig;
    }


    @Override
    public SttResponse asyncRecognizeAudio(SttRequest request) {
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
            .model(openAIClientConfig.getAudioModel())
            .file(request.audioFile())
            .build();

        WhisperTranscriptionResponse transcription = openAIClient.createTranscription(whisperTranscriptionRequest);

        return SttResponse.to(transcription.getText());
    }

}
