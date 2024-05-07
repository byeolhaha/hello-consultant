package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryRequest;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import io.micrometer.common.util.StringUtils;
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

    public ChatMessageGetRepositoryResponses getChatMessageByCursor(ChatMessageGetRepositoryRequest request) {
        Query query = new Query(
            Criteria.where("chatRoomId").is(request.chatRoomId())
                .and("messageType").ne("AUDIO"));

        if (!StringUtils.isEmpty(request.nextChatMessageId())) {
            query.addCriteria(Criteria.where("_id").lt(new ObjectId(request.nextChatMessageId())));
        }

        query.limit(request.pageSize() + 1)
            .with(Sort.by(Sort.Direction.DESC, "_id"));

        List<ChatMessage> chatMessages = mongoTemplate.find(query, ChatMessage.class);

        boolean hasNext =  hasNext(chatMessages.size(), request.pageSize());
        if (hasNext) {
            chatMessages.remove(chatMessages.size() - 1);
        }

        return ChatMessageGetRepositoryResponses.to(
            chatMessages,
            hasNext);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(new Query(), ChatMessage.class);
    }

    private static boolean hasNext(int returnSize, int pageSize) {
        return returnSize > pageSize;
    }

}
