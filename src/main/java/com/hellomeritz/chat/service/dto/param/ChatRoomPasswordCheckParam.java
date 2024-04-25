package com.hellomeritz.chat.service.dto.param;

import com.hellomeritz.chat.domain.ChatRoomPassword;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hellomeritz.member.global.encryption.dto.PasswordMatchRequest;

import java.time.LocalDateTime;

public record ChatRoomPasswordCheckParam(
        long chatRoomId,
        String chatRoomPassword
) {
    public PasswordMatchRequest toPasswordMatchRequest(ChatRoomPasswordInfo chatRoomPasswordInfo) {
        return new PasswordMatchRequest(
                chatRoomPassword.toCharArray(),
                chatRoomPasswordInfo.getChatRoomPassword(),
                chatRoomPasswordInfo.getSalt()
        );
    }

    public ChatRoomPasswordCreateParam toChatRoomPasswordCreateRequest() {
        return new ChatRoomPasswordCreateParam(
                ChatRoomPassword.of(chatRoomPassword),
                chatRoomId,
                LocalDateTime.now()
        );
    }
}
