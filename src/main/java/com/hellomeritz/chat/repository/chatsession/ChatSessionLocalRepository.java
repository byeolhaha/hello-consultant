package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatSessionLocalRepository {

    private Map<String, ChatRoomEntry> sessions = new ConcurrentHashMap<>();

    public void addSession(ChatSessionAddRepositoryRequest request) {
        sessions.put(request.sessionId(), request.toChatRoomEntry());
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public ChatRoomEntry getChatRoomEntry(String sessionId) {
        return sessions.get(sessionId);
    }
}
