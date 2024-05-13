package com.hello.chat.repository.chatentry;

import com.hello.chat.repository.chatentry.dto.ChatRoomEntryAddRepositoryRequest;
import com.hello.chat.repository.chatentry.dto.ChatRoomEntryDeleteRepositoryRequest;
import com.hello.chat.repository.chatentry.dto.ChatRoomEntryGetSessionRequest;

import java.util.List;
import java.util.Map;

public interface ChatRoomEntryRepository {

    void addMemberToRoom(ChatRoomEntryAddRepositoryRequest request);

    void removeMemberFromRoom(ChatRoomEntryDeleteRepositoryRequest request);

    Map<Long, List<ChatRoomEntry>> look();

    int getAttendanceCount();

    String getSession(ChatRoomEntryGetSessionRequest request);

}
