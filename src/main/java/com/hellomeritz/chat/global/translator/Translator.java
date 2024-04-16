package com.hellomeritz.chat.global.translator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class Translator {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();

    @Value("${deepl.api.key}")
    private String secretKey;

    @Value("${deepl.api.url}")
    private String apiUrl;

    public TranslationResponse translate(TranslationRequest request) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("text", request.contents());
        parameters.add("target_lang", request.targetLang().getDeeplLang());
        parameters.add("source_lang", request.sourceLang().getDeeplLang());

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", secretKey);

        HttpEntity<?> httpEntity = new HttpEntity<>(parameters, headers);
        ResponseEntity<TranslationResponse> response =
            restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, TranslationResponse.class);

        return response.getBody();
    }


}
