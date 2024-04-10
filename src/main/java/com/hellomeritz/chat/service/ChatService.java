package com.hellomeritz.chat.service;

import com.hellomeritz.chat.global.client.Translator;
import com.hellomeritz.chat.global.client.TranslationResponse;
import com.hellomeritz.chat.repository.ChatMessageRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final Translator translater;

    public ChatService(ChatMessageRepository chatMessageRepository, Translator translater) {
        this.chatMessageRepository = chatMessageRepository;
        this.translater = translater;
    }

    @Transactional
    public ChatMessageTranslateTextResult translateText(ChatMessageTextParam param) {
        TranslationResponse translatedResponse = translater.translate(param.toTranslationRequest());

        return ChatMessageTranslateTextResult.to(
            chatMessageRepository.save(param.toChatMessage()),
            chatMessageRepository.save(param.toChatMessage(translatedResponse.getText()))
        );
    }

}
