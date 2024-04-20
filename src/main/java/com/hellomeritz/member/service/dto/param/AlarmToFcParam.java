package com.hellomeritz.member.service.dto.param;

import com.hellomeritz.member.global.sms.SmsAlarmToFcRequest;

public record AlarmToFcParam(
    long chatRoomId,
    long fcId
) {
    public SmsAlarmToFcRequest t0SmsSendRequest(String phoneNumber) {
        return new SmsAlarmToFcRequest(
            phoneNumber,
            chatRoomId
        );
    }

    public static AlarmToFcParam to(
        long chatRoomId,
        long fcId) {
        return new AlarmToFcParam(
            chatRoomId,
            fcId
        );
    }
}
