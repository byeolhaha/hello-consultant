package com.hellomeritz.chat.repository.chatroom;

import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomGetInfo;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomUserInfo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return chatRoomJpaRepository.findChatRoomByFcIdAndForeignerId(fcId, userId).orElseThrow(
            () -> new EntityNotFoundException("해당 회원들로 이뤄진 채팅방은 존재하지 않습니다.")
        );
    }

    @Override
    public ChatRoomUserInfo getChatRoomUserInfo(Long chatRoomId) {
        return chatRoomJpaRepository
            .findChatRoomUserInfo(chatRoomId)
            .orElseThrow(() -> new EntityNotFoundException("해당 채팅방은 존재하지 않습니다."));
    }

    @Override
    public ChatRoom getChatRoom(long chatRoomId) {
        return chatRoomJpaRepository
            .findById(chatRoomId)
            .orElseThrow(() -> new EntityNotFoundException("해당 채팅방은 존재하지 않습니다."));
    }

    @Override
    public ChatRoomPasswordInfo getChatRoomEnterInfo(long chatRoomId) {
        return chatRoomJpaRepository
            .findChatRoomEnterInfo(chatRoomId)
            .orElseThrow(() -> new EntityNotFoundException("해당 채팅방은 존재하지 않습니다."));
    }

    @Override
    public List<ChatRoomGetInfo> findChatRoomsByForeigner(Long foreignerId) {
        return chatRoomJpaRepository.findChatRoomsByForeigner(foreignerId);
    }

    @Override
    public List<ChatRoomGetInfo> findChatRoomsByConsultant(Long foreignerId) {
        return chatRoomJpaRepository.findChatRoomsByConsultant(foreignerId);
    }

}
