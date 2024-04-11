package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public void deleteAll() {
        chatMessageMongoRepository.deleteAll();
    }

}

