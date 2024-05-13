package com.hello.chat.service.dto.param;

import com.hello.global.encryption.PassWord;
import com.hello.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hello.global.encryption.dto.PasswordMatchRequest;

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
                PassWord.of(chatRoomPassword),
                chatRoomId,
                LocalDateTime.now()
        );
    }
}
