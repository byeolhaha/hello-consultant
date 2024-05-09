package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatsession.dto.ChatSessionAddRepositoryRequest;
import com.hellomeritz.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;

public interface ChatSessionRepository {

    void addSession(ChatSessionAddRepositoryRequest request);

    void removeSession(String sessionId);

    ChatSession getChatSession(String sessionId);

    void changeChatRoomEntry(ChatSessionChangeRepositoryRequest request);
}
