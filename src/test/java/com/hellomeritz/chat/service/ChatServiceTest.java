package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.global.exception.ErrorCode;
import com.hellomeritz.chat.global.exception.custom.SttException;
import com.hellomeritz.chat.global.stt.SttManagerHandler;
import com.hellomeritz.chat.global.stt.SttProvider;
import com.hellomeritz.chat.repository.chatmessage.ChatMessageRepository;
import com.hellomeritz.chat.repository.chatroom.ChatRoomRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageGetParam;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.param.ChatRoomCreateParam;
import com.hellomeritz.chat.service.dto.param.ChatRoomUserInfoParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomUserInfoResult;
import com.hellomeritz.global.ChatFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class ChatServiceTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private SttManagerHandler sttManagerHandler;

    @DisplayName("채팅메세지를 저장할 수 있다.")
    @Test
    void saveChatMessage() {
        // given
        ChatMessageTextParam chatMessageTextParam = ChatFixture.chatMessageTextParamByFC();

        // when
        ChatMessageTranslateResult result = chatService.translateText(chatMessageTextParam);

        // then
        assertThat(result.originContents()).isEqualTo(chatMessageTextParam.contents());
    }

    @DisplayName("채팅방을 만들 수 있다.")
    @Test
    void createChatRoom() {
        // given
        ChatRoomCreateParam chatRoomCreateParam = ChatFixture.chatRoomCreateParam();

        // when
        ChatRoomCreateResult result = chatService.createChatRoom(chatRoomCreateParam);
        ChatRoom chatRoom = chatRoomRepository.getChatRoom(
                chatRoomCreateParam.fcId(),
                chatRoomCreateParam.userId());

        // then
        assertThat(result.chatRoomId()).isEqualTo(chatRoom.getChatRoomId());
    }

    @DisplayName("채팅방 메세지를 스크롤 방식으로 확인할 수 있으며 현재부터 과거로 스크롤 방식으로 보여진다.")
    @Test
    void getChatMessages() {
        // given
        chatMessageRepository.deleteAll();

        chatMessageRepository.save(ChatFixture.translatedChatMessageByFC());
        chatMessageRepository.save(ChatFixture.originChatMessageByUser());
        ChatMessage lastChatMessage =chatMessageRepository.save(ChatFixture.translatedChatMessageByUser());

        ChatMessageGetParam chatMessageGetParam = ChatFixture.chatMessageGetParam();

        // when
        ChatMessageGetResults results = chatService.getChatMessages(chatMessageGetParam);

        // then
        assertThat(results.nextChatMessageId()).isEqualTo(lastChatMessage.getId());
        for (int i = 0; i < results.chatMessages().size() - 1; i++) {
            LocalDateTime current = LocalDateTime.parse(results.chatMessages().get(i).createdAt());
            LocalDateTime next = LocalDateTime.parse(results.chatMessages().get(i + 1).createdAt());
            assertThat(current).isBeforeOrEqualTo(next);
        }

    }

    @DisplayName("채팅방에 있는 유저들의 정보를 확인할 수 있다.")
    @Test
    void getChatRoomUserInfo() {
        // given
        ChatRoom chatRoom = ChatFixture.chatRoom();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // when
        ChatRoomUserInfoResult result = chatService.getChatRoomUserInfo(new ChatRoomUserInfoParam(savedChatRoom.getChatRoomId()));

        // then
        assertThat(result.fcId()).isEqualTo(chatRoom.getFcId());
        assertThat(result.userId()).isEqualTo(chatRoom.getUserId());
    }

}
