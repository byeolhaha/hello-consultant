package com.hellomeritz.chat.global.stt;


import com.hellomeritz.chat.global.exception.ErrorCode;
import com.hellomeritz.chat.global.exception.custom.SttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SttWhisperManager implements SttManager{

    @Value("${openAI.stt.url}")
    private String apiUrl;

    @Value("${openAI.stt.secret}")
    private String apiSecret;


    @Override
    public SttResponse asyncRecognizeAudio(SttRequest request)  {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(request.audioUrl()));

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setBearerAuth(apiSecret);

            body.add("file", request.audioUrl());
            body.add("model", "whisper-1");

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class
            );

            return SttResponse.to(response.getBody());
        }catch (IOException e) {
            throw new SttException(ErrorCode.STT_IO_ERROR);
        }
    }

    private static HttpHeaders createFileHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return headers;
    }
}
