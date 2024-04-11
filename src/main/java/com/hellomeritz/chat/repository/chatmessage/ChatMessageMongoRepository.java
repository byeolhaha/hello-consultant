package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;
import org.bson.types.ObjectId;
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

    public List<ChatMessage> getChatMessageByCursor(ChatMessageGetRepositoryRequest request) {
        Query query = new Query(
            Criteria.where("_id")
                .gt(new ObjectId(request.nextChatMessageId()))
                .and("chatRoomId").is(request.chatRoomId()))
            .limit(request.pageSize())
            .with(Sort.by(Sort.Direction.DESC, "_id"));

        return mongoTemplate.find(query, ChatMessage.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(new Query(), ChatMessage.class);
    }

}
