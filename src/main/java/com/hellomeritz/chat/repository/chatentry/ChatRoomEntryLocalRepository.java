package com.hellomeritz.chat.repository.chatentry;

import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryAddRepositoryRequest;
import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryDeleteRepositoryRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatRoomEntryLocalRepository {

    private Map<Long, List<ChatRoomEntry>> attendances = new ConcurrentHashMap<>();

    public void addMemberToRoom(ChatRoomEntryAddRepositoryRequest request) {
        attendances.computeIfAbsent(request.chatRoomId(), k -> new ArrayList<>())
            .add(request.toChatRoomEntry());
    }

    public void removeMemberFromRoom(ChatRoomEntryDeleteRepositoryRequest request) {
        attendances.get(request.chatRoomId())
            .removeIf(chatRoomEntry -> chatRoomEntry.sessionId().equals(request.sessionId()));
    }

    public Map<Long, List<ChatRoomEntry>> look() {
        return attendances;
    }

    public int getAttendanceCount() {
        return attendances.size();
    }

}
