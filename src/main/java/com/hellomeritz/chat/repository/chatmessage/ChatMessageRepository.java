package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageReadRepositoryRequest;

import java.util.List;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage);

    ChatMessageGetRepositoryResponses getChatMessageByCursor(ChatMessageGetRepositoryRequest request);

    void readPartnerMessage(ChatMessageReadRepositoryRequest request);

    void deleteAll();

}
