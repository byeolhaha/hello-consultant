package com.hello.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CircuitBreakerBot {

    private final RestTemplate restTemplate;

    public CircuitBreakerBot() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${bot.url}")
    String botUrl;

    @Value("${bot.chat-id}")
    String botChatId;

    public void sendBotMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<BotRequest> requestEntity
            = new HttpEntity<>(BotRequest.to(botChatId,message), headers);

        restTemplate.exchange(botUrl, HttpMethod.POST, requestEntity, String.class);
    }

}
