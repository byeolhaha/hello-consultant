package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatsession.dto.ChatSessionAddRepositoryRequest;
import com.hellomeritz.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatSessionLocalRepository {

    private Map<String, ChatSession> sessions = new ConcurrentHashMap<>();

    public void addSession(ChatSessionAddRepositoryRequest request) {
        sessions.put(request.sessionId(), request.toChatRoomEntry());
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public ChatSession getChatSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void changeChatRoomEntry(ChatSessionChangeRepositoryRequest request) {
        ChatSession chatSession = sessions.get(request.sessionId());
        chatSession.setChatRoomId(request.chatRoomId());

        sessions.put(request.sessionId(), chatSession);
    }
}
