package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.global.encryption.PassWord;
import com.hellomeritz.global.encryption.dto.EncryptionRequest;

import java.time.LocalDateTime;

public record ChatRoomPasswordCreateParam(
        PassWord chatRoomPassWord,
        long chatRoomId,
        LocalDateTime enterChatRoomDateTime
) {
    public EncryptionRequest toEncryptionRequest(String ipAddress) {
        return new EncryptionRequest(
                chatRoomPassWord.getPassword(),
                ipAddress,
                enterChatRoomDateTime
        );
    }
}
