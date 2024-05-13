package com.hello.chat.repository.chatsession;

import com.hello.chat.repository.chatsession.dto.ChatSessionAddRepositoryRequest;
import com.hello.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;
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
        ChatSession chatSession = sessions.get(sessionId);
        if (chatSession == null) {
            throw new IllegalArgumentException("세션 정보를 찾을 수 없습니다: " + sessionId);
        }
        return chatSession;
    }

    public void changeChatRoomEntry(ChatSessionChangeRepositoryRequest request) {
        ChatSession chatSession = sessions.get(request.sessionId());
        chatSession.setChatRoomId(request.chatRoomId());

        sessions.put(request.sessionId(), chatSession);
    }
}
