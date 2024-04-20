package com.hellomeritz.member.global;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CoolSmsManager implements SmsManager{

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${coolsms.api.url}")
    private String coolSmsUrl;

    @Value("${coolsms.dispatcher}")
    private String dispatcherPhoneNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    private void inti() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, coolSmsUrl);
    }

    public SingleMessageSentResponse sendMessage(String outgoingPhoneNumber, String content) {
        Message message = new Message();
        message.setFrom(dispatcherPhoneNumber);
        message.setTo(outgoingPhoneNumber);
        message.setText(content);

        return messageService.sendOne(new SingleMessageSendingRequest(message));
    }

}
