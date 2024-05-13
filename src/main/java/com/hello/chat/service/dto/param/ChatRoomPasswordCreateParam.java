package com.hello.chat.service.dto.param;

import com.hello.global.encryption.PassWord;
import com.hello.global.encryption.dto.EncryptionRequest;

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
