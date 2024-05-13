package com.hello.chat.repository.chatsession;

import com.hello.chat.repository.chatsession.dto.ChatSessionAddRepositoryRequest;
import com.hello.chat.repository.chatsession.dto.ChatSessionChangeRepositoryRequest;

public interface ChatSessionRepository {

    void addSession(ChatSessionAddRepositoryRequest request);

    void removeSession(String sessionId);

    ChatSession getChatSession(String sessionId);

    void changeChatRoomEntry(ChatSessionChangeRepositoryRequest request);
}
