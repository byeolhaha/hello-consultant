package com.hello.chat.repository.chatmessage;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.repository.chatmessage.dto.*;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageMongoRepository chatMessageMongoRepository;

    public ChatMessageRepositoryImpl(ChatMessageMongoRepository chatMessageMongoRepository) {
        this.chatMessageMongoRepository = chatMessageMongoRepository;
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageMongoRepository.save(chatMessage);
    }

    @Override
    public ChatMessageGetRepositoryResponses getChatMessageByCursor(ChatMessageGetRepositoryRequest request) {
        return chatMessageMongoRepository.getChatMessageByCursor(request);
    }

    @Override
    public void readPartnerMessage(ChatMessageReadRepositoryRequest request) {
        chatMessageMongoRepository.readPartnerMessage(request);
    }

    @Override
    public ChatMessageRecentGetRepositoryResponse getRecentChatMessages(ChatMessageRecentGetRepositoryRequest request) {
        return chatMessageMongoRepository.getRecentChatMessages(request);
    }

    @Override
    public void deleteAll() {
        chatMessageMongoRepository.deleteAll();
    }

}

