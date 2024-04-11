package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;

import java.util.List;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> getChatMessageByCursor(ChatMessageGetRepositoryRequest request);

    void deleteAll();

}
