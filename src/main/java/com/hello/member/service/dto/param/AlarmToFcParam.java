package com.hello.member.service.dto.param;

import com.hello.member.global.sms.SmsAlarmToFcRequest;

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
