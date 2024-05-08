package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.global.stt.SttManager;
import com.hellomeritz.chat.global.stt.SttManagerHandler;
import com.hellomeritz.chat.global.stt.SttProvider;
import com.hellomeritz.chat.global.stt.dto.SttResponse;
import com.hellomeritz.chat.global.translator.TranslateProvider;
import com.hellomeritz.chat.global.translator.TranslationResponse;
import com.hellomeritz.chat.global.translator.Translator;
import com.hellomeritz.chat.global.translator.TranslatorHandler;
import com.hellomeritz.chat.global.uploader.AudioUploadResponse;
import com.hellomeritz.chat.global.uploader.AudioUploader;
import com.hellomeritz.chat.repository.chatmessage.ChatMessageRepository;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import com.hellomeritz.chat.repository.chatroom.ChatRoomRepository;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomUserInfo;
import com.hellomeritz.chat.service.dto.param.*;
import com.hellomeritz.chat.service.dto.result.*;
import com.hellomeritz.global.CircuitBreakerBot;
import com.hellomeritz.member.global.IpSensor;
import com.hellomeritz.member.global.encryption.PasswordEncoder;
import com.hellomeritz.member.global.encryption.dto.EncryptionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ChatService {
    private static final String SIMPLE_CIRCUIT_BREAKER_CONFIG = "simpleCircuitBreakerConfig";
    private static final String TRANSLATION_FALLBACK_BOT_MESSAGE = "번역 관련 fallback 메서드가 호출되었습니다.";
    private static final int CHAT_PAGE_SIZE = 20;

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final TranslatorHandler translatorHandler;
    private final AudioUploader audioUploader;
    private final SttManagerHandler sttManagerHandler;
    private final PasswordEncoder passwordEncoder;
    private final IpSensor ipSensor;
    private final CircuitBreakerBot circuitBreakerBot;


    public ChatService(
        ChatMessageRepository chatMessageRepository,
        ChatRoomRepository chatRoomRepository, TranslatorHandler translatorHandler,
        AudioUploader audioUploader,
        SttManagerHandler sttManagerHandler,
        PasswordEncoder passwordEncoder,
        IpSensor ipSensor, CircuitBreakerBot circuitBreakerBot) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.translatorHandler = translatorHandler;
        this.audioUploader = audioUploader;
        this.sttManagerHandler = sttManagerHandler;
        this.passwordEncoder = passwordEncoder;
        this.ipSensor = ipSensor;
        this.circuitBreakerBot = circuitBreakerBot;
    }

    @CircuitBreaker(name = SIMPLE_CIRCUIT_BREAKER_CONFIG, fallbackMethod = "fallbackSendMessage")
    @Transactional
    public ChatMessageTranslateResults translateText(ChatMessageTextParam param) {
        Translator translator = translatorHandler.getTranslator(TranslateProvider.DEEPL.name());
        TranslationResponse translatedResponse = translator.translate(param.toTranslationRequest());

        return ChatMessageTranslateResults.to(
            chatMessageRepository.save(param.toChatMessage()),
            chatMessageRepository.save(param.toChatMessage(translatedResponse.translatedText()))
        );
    }

    private ChatMessageTranslateResults fallbackSendMessage(ChatMessageTextParam param, Exception exception) {
        circuitBreakerBot.sendBotMessage(TRANSLATION_FALLBACK_BOT_MESSAGE+", exception: "+exception.getMessage());

        Translator translator = translatorHandler.getTranslator(TranslateProvider.GOOGLE.name());
        TranslationResponse translatedResponse = translator.translate(param.toTranslationRequest());

        return ChatMessageTranslateResults.to(
            chatMessageRepository.save(param.toChatMessage()),
            chatMessageRepository.save(param.toChatMessage(translatedResponse.translatedText()))
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

    public ChatMessageGetResults getChatMessages(ChatMessageGetParam param) {
        ChatMessageGetRepositoryResponses chatMessages = chatMessageRepository.getChatMessageByCursor(
            param.toChatMessageGetRepositoryRequest(CHAT_PAGE_SIZE));

        return ChatMessageGetResults.to(chatMessages);
    }

    private ChatAudioUploadResult uploadAudioFile(ChatAudioUploadParam param) {
        AudioUploadResponse audioUploadResponse = audioUploader.upload(param.audioFile());
        ChatMessage chatMessageByStt = chatMessageRepository.save(audioUploadResponse.toChatMessage(param));

        return ChatAudioUploadResult.to(
            audioUploadResponse.audioUrl(),
            chatMessageByStt.getCreatedAt()
        );
    }

    @Transactional
    public ChatMessageSttResult sendAudioMessage(ChatMessageSttParam param) {
        ChatAudioUploadResult chatAudioUploadResult = uploadAudioFile(param.toChatAudioUploadParam());

        SttManager sttManager = sttManagerHandler.getSttManager(SttProvider.GOOGLE.name());
        SttResponse textBySpeech
            = sttManager.asyncRecognizeAudio(param.toSttRequest(chatAudioUploadResult.audioUrl()));
        ChatMessage chatMessage = chatMessageRepository.save(textBySpeech.toChatMessage(param));

        return ChatMessageSttResult.to(
            textBySpeech.textBySpeech(),
            chatMessage.getCreatedAt(),
            SttProvider.GOOGLE.name()
        );
    }

    private ChatMessageSttResult fallbackSendAudioMessage(ChatMessageSttParam param, Exception e) {
        SttManager sttManager = sttManagerHandler.getSttManager(SttProvider.WHISPER.name());
        SttResponse textBySpeech = sttManager.asyncRecognizeAudio(param.toEmptySttRequest());
        ChatMessage chatMessage = chatMessageRepository.save(textBySpeech.toChatMessage(param));

        return ChatMessageSttResult.to(
            textBySpeech.textBySpeech(),
            chatMessage.getCreatedAt(),
            SttProvider.WHISPER.name()
        );
    }

    public ChatRoomUserInfoResult getChatRoomUserInfo(ChatRoomUserInfoParam param) {
        ChatRoomUserInfo chatRoomUserInfo = chatRoomRepository.getChatRoomUserInfo(param.chatRoomId());

        return ChatRoomUserInfoResult.to(chatRoomUserInfo);
    }

    @Transactional
    public void createChatRoomPassword(ChatRoomPasswordCreateParam request) {
        String clientIp = ipSensor.getClientIP();
        EncryptionResponse encryptionResponse
            = passwordEncoder.encrypt(request.toEncryptionRequest(clientIp));
        ChatRoom chatRoom = chatRoomRepository.getChatRoom(request.chatRoomId());
        chatRoom.setChatRoomPassword(encryptionResponse.password());
        chatRoom.setSalt(encryptionResponse.salt());
    }

    @Transactional
    public boolean checkChatRoomPassword(ChatRoomPasswordCheckParam request) {
        ChatRoomPasswordInfo chatRoomEnterInfo = chatRoomRepository.getChatRoomEnterInfo(request.chatRoomId());
        boolean isAuthorized = passwordEncoder.matchPassword(request.toPasswordMatchRequest(chatRoomEnterInfo));

        if (isAuthorized) {
            createChatRoomPassword(request.toChatRoomPasswordCreateRequest());
        }
        return isAuthorized;
    }

}
