package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;
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
    public ChatRoomEntry getChatRoomEntry(String sessionId) {
        return chatSessionLocalRepository.getChatRoomEntry(sessionId);
    }
}
