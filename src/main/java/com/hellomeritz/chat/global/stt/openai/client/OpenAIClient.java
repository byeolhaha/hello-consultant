package com.hellomeritz.chat.global.stt.openai.client;

import com.hellomeritz.chat.global.stt.openai.dto.WhisperTranscriptionRequest;
import com.hellomeritz.chat.global.stt.openai.dto.WhisperTranscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "openai-service",
    url = "${openai-service.urls.base-url}",
    configuration = OpenAIClientConfig.class
)
public interface OpenAIClient {

    @PostMapping(value = "${openai-service.urls.create-transcription-url}", headers = {"Content-Type=multipart/form-data"})
    WhisperTranscriptionResponse createTranscription(@ModelAttribute WhisperTranscriptionRequest whisperTranscriptionRequest);
}
