package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
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

}

