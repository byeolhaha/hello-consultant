package com.hello.chat.repository.chatmessage;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.repository.chatmessage.dto.*;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage);

    ChatMessageGetRepositoryResponses getChatMessageByCursor(ChatMessageGetRepositoryRequest request);

    void readPartnerMessage(ChatMessageReadRepositoryRequest request);

    ChatMessageRecentGetRepositoryResponse getRecentChatMessages(ChatMessageRecentGetRepositoryRequest request);

    void deleteAll();

}
