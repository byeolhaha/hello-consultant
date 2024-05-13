package com.hello.member.global.sms;

public record SmsAlarmToFcRequest(
    String outgoingPhoneNumber,
    long chatRoomId
) {
}
