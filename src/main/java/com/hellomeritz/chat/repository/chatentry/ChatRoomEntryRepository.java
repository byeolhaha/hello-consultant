package com.hellomeritz.chat.repository.chatentry;

import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryAddRepositoryRequest;
import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryDeleteRepositoryRequest;
import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryFindRepositoryRequest;

import java.util.List;
import java.util.Map;

public interface ChatRoomEntryRepository {

    void addMemberToRoom(ChatRoomEntryAddRepositoryRequest request);

    void removeMemberFromRoom(ChatRoomEntryDeleteRepositoryRequest request);

    boolean isMemberInRoom(ChatRoomEntryFindRepositoryRequest request);

    Map<Long, List<ChatRoomEntry>> look();
}
