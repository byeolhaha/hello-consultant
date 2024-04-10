package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.global.client.Translator;
import com.hellomeritz.chat.global.client.TranslationResponse;
import com.hellomeritz.chat.repository.chatmessage.ChatMessageRepository;
import com.hellomeritz.chat.repository.chatroom.ChatRoomRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.param.ChatRoomCreateParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final Translator translator;

    public ChatService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository, Translator translator) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.translator = translator;
    }

    @Transactional
    public ChatMessageTranslateTextResult translateText(ChatMessageTextParam param) {
        TranslationResponse translatedResponse = translator.translate(param.toTranslationRequest());

        return ChatMessageTranslateTextResult.to(
            chatMessageRepository.save(param.toChatMessage()),
            chatMessageRepository.save(param.toChatMessage(translatedResponse.getText()))
        );
    }

    @Transactional
    public ChatRoomCreateResult createChatRoom(ChatRoomCreateParam param) {
        return ChatRoomCreateResult.to(
            chatRoomRepository.save(
                ChatRoom.of(param.fcId(), param.userId())
            )
        );

    }

}
