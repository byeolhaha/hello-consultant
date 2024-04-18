package com.hellomeritz.chat.repository.chatroom;

import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomUserInfo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomJpaRepository chatRoomJpaRepository;

    public ChatRoomRepositoryImpl(ChatRoomJpaRepository chatRoomJpaRepository) {
        this.chatRoomJpaRepository = chatRoomJpaRepository;
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomJpaRepository.save(chatRoom);
    }

    @Override
    public ChatRoom getChatRoom(long fcId, long userId) {
        return chatRoomJpaRepository.findChatRoomByFcIdAndUserId(fcId, userId).orElseThrow(
            () -> new EntityNotFoundException("해당 회원들로 이뤄진 채팅방은 존재하지 않습니다.")
        );
    }

    @Override
    public ChatRoomUserInfo getChatRoomUserInfo(Long chatRoomId) {
        return chatRoomJpaRepository
                .findChatRoomUserInfo(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 채팅방은 존재하지 않습니다."));
    }
}
