package com.hellomeritz.member.global;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsManager {
    SingleMessageSentResponse sendMessage(String outgoingPhoneNumber, String content);
}
