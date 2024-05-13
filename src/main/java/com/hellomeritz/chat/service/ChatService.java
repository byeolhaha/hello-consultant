package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.repository.chatentry.ChatRoomEntryRepository;
import com.hellomeritz.chat.repository.chatmessage.ChatMessageRepository;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import com.hellomeritz.chat.repository.chatmessage.dto.ChatMessageRecentGetRepositoryResponse;
import com.hellomeritz.chat.repository.chatroom.ChatRoomRepository;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomGetInfoOfConsultant;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomGetInfoOfForeigner;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomUserInfo;
import com.hellomeritz.chat.repository.chatsession.ChatSession;
import com.hellomeritz.chat.repository.chatsession.ChatSessionRepository;
import com.hellomeritz.chat.service.dto.param.*;
import com.hellomeritz.chat.service.dto.result.*;
import com.hellomeritz.member.global.IpSensor;
import com.hellomeritz.global.encryption.PasswordEncoder;
import com.hellomeritz.global.encryption.dto.EncryptionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final int CHAT_PAGE_SIZE = 20;

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PasswordEncoder passwordEncoder;
    private final IpSensor ipSensor;
    private final ChatRoomEntryRepository chatRoomEntryRepository;
    private final ChatSessionRepository chatSessionRepository;


    public ChatService(
        ChatMessageRepository chatMessageRepository,
        ChatRoomRepository chatRoomRepository,
        PasswordEncoder passwordEncoder,
        IpSensor ipSensor,
        ChatRoomEntryRepository chatRoomEntryRepository,
        ChatSessionRepository chatSessionRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.passwordEncoder = passwordEncoder;
        this.ipSensor = ipSensor;
        this.chatRoomEntryRepository = chatRoomEntryRepository;
        this.chatSessionRepository = chatSessionRepository;
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

    public ChatRoomUserInfoResult getChatRoomUserInfo(ChatRoomUserInfoParam param) {
        ChatRoomUserInfo chatRoomUserInfo = chatRoomRepository.getChatRoomUserInfo(param.chatRoomId());

        return ChatRoomUserInfoResult.to(chatRoomUserInfo);
    }

    @Transactional
    public void createChatRoomPassword(ChatRoomPasswordCreateParam param) {
        String clientIp = ipSensor.getClientIP();
        EncryptionResponse encryptionResponse
            = passwordEncoder.encrypt(param.toEncryptionRequest(clientIp));
        ChatRoom chatRoom = chatRoomRepository.getChatRoom(param.chatRoomId());
        chatRoom.setChatRoomPassword(encryptionResponse.password());
        chatRoom.setSalt(encryptionResponse.salt());
    }

    @Transactional
    public boolean checkChatRoomPassword(ChatRoomPasswordCheckParam param) {
        ChatRoomPasswordInfo chatRoomEnterInfo = chatRoomRepository.getChatRoomEnterInfo(param.chatRoomId());
        boolean isAuthorized = passwordEncoder.matchPassword(param.toPasswordMatchRequest(chatRoomEnterInfo));

        if (isAuthorized) {
            createChatRoomPassword(param.toChatRoomPasswordCreateRequest());
        }
        return isAuthorized;
    }


    public void addSession(ChatSessionAddParam param) {
        chatSessionRepository.addSession(param.toChatSessionAddRepositoryRequest());
    }

    public ChatRoomEntryInSessions getChatEntryInSessions(String sessionId) {
        return ChatRoomEntryInSessions.to(chatSessionRepository.getChatSession(sessionId));
    }

    public void addChatRoomEntry(ChatRoomEntryAddParam param) {
        chatRoomEntryRepository.addMemberToRoom(param.toChatRoomEntryAddRepositoryRequest());
    }

    public void changeSession(ChatSessionChangeParam param) {
        chatSessionRepository.changeChatRoomEntry(param.toChatSessionChangeRepositoryRequest());
    }

    public void leaveChatRoom(ChatRoomLeaveParam param) {
        String sessionId = chatRoomEntryRepository.getSession(param.toChatRoomEntryGetSessionRequest());
        leaveChatRoom(sessionId);
    }

    public void leaveChatRoom(String sessionId) {
        ChatSession chatSession = chatSessionRepository.getChatSession(sessionId);
        chatRoomEntryRepository.removeMemberFromRoom(chatSession.toChatRoomEntryDeleteRepositoryRequest(sessionId));
        chatSessionRepository.removeSession(sessionId);
    }

    public void enterChatRoom(ChatRoomEnterParam param) {
        chatMessageRepository.readPartnerMessage(param.toChatMessageReadRepositoryRequest());
    }

    public ChatRoomInfoOfConsultantResults getChatRoomInfoOfConsultant(ChatRoomInfoOfConsultantParam param) {
        List<ChatRoomGetInfoOfConsultant> chatRoomGetInfoOfConsultant = chatRoomRepository.findChatRoomsOfConsultant(param.userId());

        List<ChatRoomInfoOfConsultantResult> responses = new ArrayList<>();
        for (ChatRoomGetInfoOfConsultant chatRoom : chatRoomGetInfoOfConsultant) {
            ChatMessageRecentGetRepositoryResponse chatRoomInfo = chatMessageRepository.getRecentChatMessages(
                param.toChatMessageRecentGetRepositoryRequest(chatRoom.getChatRoomId()));

            responses.add(ChatRoomInfoOfConsultantResult.to(chatRoom, chatRoomInfo));
        }

        return ChatRoomInfoOfConsultantResults.to(responses);
    }

    public ChatRoomInfoOfForeignerResults getChatRoomInfoOfForeigner(ChatRoomInfoOfForeignerParam param) {
        List<ChatRoomGetInfoOfForeigner> chatRoomGetInfoOfConsultant = chatRoomRepository.findChatRoomsOfForeigner(param.userId());

        List<ChatRoomInfoOfForeignerResult> responses = new ArrayList<>();
        for (ChatRoomGetInfoOfForeigner chatRoom : chatRoomGetInfoOfConsultant) {
            ChatMessageRecentGetRepositoryResponse chatRoomInfo = chatMessageRepository.getRecentChatMessages(
                param.toChatMessageRecentGetRepositoryRequest(chatRoom.getChatRoomId()));

            responses.add(ChatRoomInfoOfForeignerResult.to(chatRoom, chatRoomInfo));
        }

        return ChatRoomInfoOfForeignerResults.to(responses);
    }

}
