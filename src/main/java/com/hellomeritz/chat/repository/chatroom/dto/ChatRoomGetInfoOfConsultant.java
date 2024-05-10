package com.hellomeritz.chat.repository.chatroom.dto;

import java.time.LocalDateTime;

public interface ChatRoomGetInfoOfConsultant {

    Long getChatRoomId();
    LocalDateTime getCreatedAt();
    String getName();
}