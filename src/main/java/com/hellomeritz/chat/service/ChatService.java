package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.global.stt.SttGoogleManager;
import com.hellomeritz.chat.global.stt.SttManager;
import com.hellomeritz.chat.global.stt.SttResponse;
import com.hellomeritz.chat.global.translator.Translator;
import com.hellomeritz.chat.global.translator.TranslationResponse;
import com.hellomeritz.chat.global.uploader.AudioUploadResponse;
import com.hellomeritz.chat.global.uploader.AudioUploader;
import com.hellomeritz.chat.repository.chatmessage.ChatMessageRepository;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import com.hellomeritz.chat.repository.chatroom.ChatRoomRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageGetParam;
import com.hellomeritz.chat.service.dto.param.ChatMessageSttParam;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.param.ChatRoomCreateParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;
import com.hellomeritz.chat.service.dto.result.ChatMessageSttResult;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChatService {
    private static final int CHAT_PAGE_SIZE = 10;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final Translator translator;
    private final AudioUploader audioUploader;
    private final SttManager sttManager;

    public ChatService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository, Translator translator, AudioUploader audioUploader, SttManager sttManager) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.translator = translator;
        this.audioUploader = audioUploader;
        this.sttManager = sttManager;
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

    @Transactional(readOnly = true)
    public ChatMessageGetResults getChatMessages(ChatMessageGetParam param) {
        ChatMessageGetRepositoryResponses chatMessages = chatMessageRepository.getChatMessageByCursor(
            param.toChatMessageGetRepositoryRequest(CHAT_PAGE_SIZE));

        return ChatMessageGetResults.to(
            chatMessages,
            param.myId());
    }

    @Transactional
    public ChatMessageSttResult sendAudioMessage(ChatMessageSttParam param) {
        AudioUploadResponse audioUploadResponse = audioUploader.upload(param.audioFile());
        SttResponse textBySpeech = sttManager.asyncRecognizeGcs(audioUploadResponse, param.sourceLang());

        ChatMessage chatMessageByStt = chatMessageRepository.save(audioUploadResponse.toChatMessage(param));
        chatMessageRepository.save(textBySpeech.toChatMessage(param));

        return ChatMessageSttResult.to(
            audioUploadResponse.audioUrl(),
            textBySpeech.textBySpeech(),
            chatMessageByStt.getCreatedAt()
        );

    }

}
