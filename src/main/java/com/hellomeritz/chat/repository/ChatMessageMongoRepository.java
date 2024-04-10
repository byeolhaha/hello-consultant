package com.hellomeritz.chat.repository;

import com.hellomeritz.chat.domain.ChatMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMessageMongoRepository {

    private final MongoOperations mongoTemplate;

    public ChatMessageMongoRepository(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        return mongoTemplate.save(chatMessage);
    }

    public List<ChatMessage> getChatMessageByCursor(String referenceId, int pageSize) {
        Query query = new Query(Criteria.where("_id").lt(referenceId)).limit(pageSize)
            .with(Sort.by(Sort.Direction.DESC, "_id"));

        return mongoTemplate.find(query, ChatMessage.class);
    }

}
