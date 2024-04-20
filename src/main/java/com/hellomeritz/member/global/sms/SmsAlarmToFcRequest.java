package com.hellomeritz.member.global.sms;

public record SmsAlarmToFcRequest(
    String outgoingPhoneNumber,
    long chatRoomId
) {
}
