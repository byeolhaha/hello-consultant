package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.domain.ChatRoomPassword;
import com.hellomeritz.member.global.encryption.dto.EncryptionRequest;

import java.time.LocalDateTime;

public record ChatRoomPasswordCreateParam(
        ChatRoomPassword chatRoomPassWord,
        long chatRoomId,
        LocalDateTime enterChatRoomDateTime
) {
    public EncryptionRequest toEncryptionRequest(String ipAddress) {
        return new EncryptionRequest(
                chatRoomPassWord.getChatRoomPassword(),
                ipAddress,
                enterChatRoomDateTime
        );
    }
}
