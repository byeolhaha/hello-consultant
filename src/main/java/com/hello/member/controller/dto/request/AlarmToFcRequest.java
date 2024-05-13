package com.hello.member.controller.dto.request;

import jakarta.validation.constraints.Positive;

public record AlarmToFcRequest(
    @Positive(message = "chatRoomId는 양수여야 합니다.")
    long chatRoomId,

    long fcId
) {
}
