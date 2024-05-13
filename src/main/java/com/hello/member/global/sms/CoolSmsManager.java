package com.hello.member.global.sms;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CoolSmsManager implements SmsManager {

    private static final String FOREIGNER_ARRIVAL_MESSAGE = "%s번 방에 외국인 고객이 입장하였습니다.";

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

    public SingleMessageSentResponse sendAlarmMessage(SmsAlarmToFcRequest request) {
        Message message = new Message();
        message.setFrom(dispatcherPhoneNumber);
        message.setTo(request.outgoingPhoneNumber());
        message.setText(String.format(FOREIGNER_ARRIVAL_MESSAGE,request.chatRoomId()));

        return messageService.sendOne(new SingleMessageSendingRequest(message));
    }

}
