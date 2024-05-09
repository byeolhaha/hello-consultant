package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;

public interface ChatSessionRepository {

    void addSession(ChatSessionAddRepositoryRequest request);

    void removeSession(String sessionId);

    ChatRoomEntry getChatRoomEntry(String sessionId);
}
