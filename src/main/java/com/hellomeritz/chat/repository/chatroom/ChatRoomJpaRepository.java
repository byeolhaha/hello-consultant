package com.hellomeritz.chat.repository.chatroom;

import com.hellomeritz.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findChatRoomByFcIdAndUserId(long fcId, long userId);
}
