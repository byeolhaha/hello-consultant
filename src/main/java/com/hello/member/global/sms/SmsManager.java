package com.hello.member.global.sms;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsManager {
    SingleMessageSentResponse sendAlarmMessage(SmsAlarmToFcRequest request);
}
