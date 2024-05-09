package com.hellomeritz.chat.repository.chatentry;

import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryAddRepositoryRequest;
import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryDeleteRepositoryRequest;
import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryFindRepositoryRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Component
public class ChatRoomEntryRepositoryImpl implements ChatRoomEntryRepository{

    private final ChatRoomEntryLocalRepository chatRoomEntryLocalRepository;

    public ChatRoomEntryRepositoryImpl(ChatRoomEntryLocalRepository chatRoomEntryLocalRepository) {
        this.chatRoomEntryLocalRepository = chatRoomEntryLocalRepository;
    }

    @Override
    public void addMemberToRoom(ChatRoomEntryAddRepositoryRequest request) {
        chatRoomEntryLocalRepository.addMemberToRoom(request);
    }

    @Override
    public void removeMemberFromRoom(ChatRoomEntryDeleteRepositoryRequest request) {
        chatRoomEntryLocalRepository.removeMemberFromRoom(request);

    }

    @Override
    public boolean isMemberInRoom(ChatRoomEntryFindRepositoryRequest request) {
        return chatRoomEntryLocalRepository.isMemberInRoom(request);
    }

    @Override
    public Map<Long, List<ChatRoomEntry>> look() {
        return chatRoomEntryLocalRepository.look();
    }
}