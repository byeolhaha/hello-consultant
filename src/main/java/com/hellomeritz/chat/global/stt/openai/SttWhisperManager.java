package com.hellomeritz.chat.global.stt;


import com.hellomeritz.chat.global.exception.ErrorCode;
import com.hellomeritz.chat.global.exception.custom.SttException;
import com.hellomeritz.chat.global.uploader.AudioUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SttWhisperManager implements SttManager {

    private static final String GOOGLE_BUCKET_PATH = "https://storage.googleapis.com/meritz-audio/";

    private final AudioUploader audioUploader;

    @Value("${open-at.stt.url}")
    private String apiUrl;

    @Value("${open-at.stt.secret}")
    private String apiSecret;

    public SttWhisperManager(AudioUploader audioUploader) {
        this.audioUploader = audioUploader;
    }


    @Override
    public SttResponse asyncRecognizeAudio(SttRequest request) {
        // HTTP 요청 준비
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", apiSecret);
        // 파일 및 모델 추가
        body.add("file", request.audioFile());
        body.add("model", "whisper-1");

        // REST 템플릿으로 POST 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SttWhisperResponse> responseEntity = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            requestEntity,
            SttWhisperResponse.class
        );

        SttWhisperResponse responseBody = responseEntity.getBody();

        return SttResponse.to(responseBody.getText());
    }

}
