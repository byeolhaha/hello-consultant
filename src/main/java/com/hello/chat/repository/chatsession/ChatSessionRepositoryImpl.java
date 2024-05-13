package com.hello.chat.repository.chatsession;

import com.hello.chat.repository.chatsession.dto.ChatSessionAddRepositoryRequest;
import com.hello.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;
import org.springframework.stereotype.Component;

@Component
public class ChatSessionRepositoryImpl implements ChatSessionRepository {

    private final ChatSessionLocalRepository chatSessionLocalRepository;

    public ChatSessionRepositoryImpl(ChatSessionLocalRepository chatSessionLocalRepository) {
        this.chatSessionLocalRepository = chatSessionLocalRepository;
    }

    @Override
    public void addSession(ChatSessionAddRepositoryRequest request) {
        chatSessionLocalRepository.addSession(request);
    }

    @Override
    public void removeSession(String sessionId) {
        chatSessionLocalRepository.removeSession(sessionId);
    }

    @Override
    public ChatSession getChatSession(String sessionId) {
        return chatSessionLocalRepository.getChatSession(sessionId);
    }

    @Override
    public void changeChatRoomEntry(ChatSessionChangeRepositoryRequest request) {
        chatSessionLocalRepository.changeChatRoomEntry(request);
    }
}
