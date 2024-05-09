package com.hellomeritz.chat.service.dto.param;

public record ChatRoomGetParam(
    long userId,
    boolean isFC
) {
}
