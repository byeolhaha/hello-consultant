package com.hellomeritz.chat.service;

import com.hellomeritz.chat.repository.ChatMessageRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessageTranslateTextResult translateText(ChatMessageTextParam param) {
        return ChatMessageTranslateTextResult.to(
            chatMessageRepository.save(param.toChatMessage())
        );
    }

}
