package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.*;

import java.util.List;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage);

    ChatMessageGetRepositoryResponses getChatMessageByCursor(ChatMessageGetRepositoryRequest request);

    void readPartnerMessage(ChatMessageReadRepositoryRequest request);

    ChatMessageRecentGetRepositoryResponse getRecentChatMessages(ChatMessageRecentGetRepositoryRequest request);

    void deleteAll();

}
