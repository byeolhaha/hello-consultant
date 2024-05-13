package com.hello.chat.service;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.global.stt.SttManager;
import com.hello.chat.global.stt.SttManagerHandler;
import com.hello.chat.global.stt.SttProvider;
import com.hello.chat.global.stt.dto.SttResponse;
import com.hello.chat.global.translator.TranslateProvider;
import com.hello.chat.global.translator.TranslationResponse;
import com.hello.chat.global.translator.Translator;
import com.hello.chat.global.translator.TranslatorHandler;
import com.hello.chat.global.uploader.AudioUploadResponse;
import com.hello.chat.global.uploader.AudioUploader;
import com.hello.chat.repository.chatentry.ChatRoomEntryRepository;
import com.hello.chat.repository.chatmessage.ChatMessageRepository;
import com.hello.chat.service.dto.param.ChatAudioUploadParam;
import com.hello.chat.service.dto.param.ChatMessageSttParam;
import com.hello.chat.service.dto.param.ChatMessageTextParam;
import com.hello.chat.service.dto.result.ChatAudioUploadResult;
import com.hello.chat.service.dto.result.ChatMessageSttResult;
import com.hello.chat.service.dto.result.ChatMessageTranslateResults;
import com.hello.global.CircuitBreakerBot;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessageService {

    private static final String SIMPLE_CIRCUIT_BREAKER_CONFIG = "simpleCircuitBreakerConfig";
    private static final String TRANSLATION_FALLBACK_BOT_MESSAGE = "번역 관련 fallback 메서드가 호출되었습니다.";
    private static final int MAX_ATTENDANCE_COUNT = 2;

    private final ChatMessageRepository chatMessageRepository;
    private final TranslatorHandler translatorHandler;
    private final AudioUploader audioUploader;
    private final SttManagerHandler sttManagerHandler;
    private final CircuitBreakerBot circuitBreakerBot;
    private final ChatRoomEntryRepository chatRoomEntryRepository;
    private final BanWordFilter banWordFilter;

    public ChatMessageService(
        ChatMessageRepository chatMessageRepository,
        TranslatorHandler translatorHandler,
        AudioUploader audioUploader,
        SttManagerHandler sttManagerHandler,
        CircuitBreakerBot circuitBreakerBot,
        ChatRoomEntryRepository chatRoomEntryRepository,
        BanWordFilter banWordFilter) {
        this.chatMessageRepository = chatMessageRepository;
        this.translatorHandler = translatorHandler;
        this.audioUploader = audioUploader;
        this.sttManagerHandler = sttManagerHandler;
        this.circuitBreakerBot = circuitBreakerBot;
        this.chatRoomEntryRepository = chatRoomEntryRepository;
        this.banWordFilter = banWordFilter;
    }

    public ChatMessageTranslateResults translateText(ChatMessageTextParam param) {
        String banWords = banWordFilter.validate(param.sourceLang().name(), param.contents());
        if (!banWords.isEmpty()) {
            return ChatMessageTranslateResults.toBanChatMessage(banWords, param.sourceLang());
        }
        return sendMessage(param);
    }

    @CircuitBreaker(name = SIMPLE_CIRCUIT_BREAKER_CONFIG, fallbackMethod = "fallbackSendMessage")
    @Transactional
    public ChatMessageTranslateResults sendMessage(ChatMessageTextParam param) {
        banWordFilter.validate(param.sourceLang().name(), param.contents());

        Translator translator = translatorHandler.getTranslator(TranslateProvider.DEEPL.name());
        TranslationResponse translatedResponse = translator.translate(param.toTranslationRequest());

        if (chatRoomEntryRepository.getAttendanceCount() == MAX_ATTENDANCE_COUNT) {
            return ChatMessageTranslateResults.to(
                chatMessageRepository.save(param.toReadChatMessage()),
                chatMessageRepository.save(param.toReadChatMessage(translatedResponse.translatedText()))
            );
        }

        return ChatMessageTranslateResults.to(
            chatMessageRepository.save(param.toNotReadChatMessage()),
            chatMessageRepository.save(param.toNotReadChatMessage(translatedResponse.translatedText()))
        );
    }

    private ChatMessageTranslateResults fallbackSendMessage(ChatMessageTextParam param, Exception exception) {
        circuitBreakerBot.sendBotMessage(TRANSLATION_FALLBACK_BOT_MESSAGE + ", exception: " + exception.getMessage());

        Translator translator = translatorHandler.getTranslator(TranslateProvider.GOOGLE.name());
        TranslationResponse translatedResponse = translator.translate(param.toTranslationRequest());

        if (chatRoomEntryRepository.getAttendanceCount() == MAX_ATTENDANCE_COUNT) {
            return ChatMessageTranslateResults.to(
                chatMessageRepository.save(param.toReadChatMessage()),
                chatMessageRepository.save(param.toReadChatMessage(translatedResponse.translatedText()))
            );
        }
        return ChatMessageTranslateResults.to(
            chatMessageRepository.save(param.toNotReadChatMessage()),
            chatMessageRepository.save(param.toNotReadChatMessage(translatedResponse.translatedText()))
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

    private ChatAudioUploadResult uploadAudioFile(ChatAudioUploadParam param) {
        AudioUploadResponse audioUploadResponse = audioUploader.upload(param.audioFile());
        ChatMessage chatMessageByStt = chatMessageRepository.save(audioUploadResponse.toChatMessage(param));

        return ChatAudioUploadResult.to(
            audioUploadResponse.audioUrl(),
            chatMessageByStt.getCreatedAt()
        );
    }

}
