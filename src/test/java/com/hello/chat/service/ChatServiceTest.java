package com.hello.chat.service;

import com.hello.chat.domain.ChatMessage;
import com.hello.chat.domain.ChatRoom;
import com.hello.chat.global.stt.SttManagerHandler;
import com.hello.chat.repository.chatentry.ChatRoomEntryRepository;
import com.hello.chat.repository.chatmessage.ChatMessageRepository;
import com.hello.chat.repository.chatmessage.dto.ChatMessageGetRepositoryResponses;
import com.hello.chat.repository.chatroom.ChatRoomRepository;
import com.hello.chat.repository.chatsession.ChatSessionRepository;
import com.hello.chat.service.dto.param.*;
import com.hello.chat.service.dto.result.*;
import com.hello.global.ChatFixture;
import com.hello.global.FinancialConsultantFixture;
import com.hello.global.ForeignFixture;
import com.hello.member.domain.FinancialConsultant;
import com.hello.member.domain.Foreigner;
import com.hello.member.repository.fc.FinancialConsultantRepository;
import com.hello.member.repository.foreign.ForeignRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
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

    @Autowired
    private FinancialConsultantRepository financialConsultantRepository;

    @Autowired
    private ForeignRepository foreignRepository;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Autowired
    private ChatRoomEntryRepository chatRoomEntryRepository;

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

        ChatMessage firstSavedMessage = chatMessageRepository.save(ChatFixture.translatedChatMessageByFC());
        chatMessageRepository.save(ChatFixture.originChatMessageByUser());
        chatMessageRepository.save(ChatFixture.translatedChatMessageByUser());

        ChatMessageGetParam chatMessageGetParam = ChatFixture.chatMessageGetParam();

        // when
        ChatMessageGetResults results = chatService.getChatMessages(chatMessageGetParam);

        // then
        assertThat(results.nextChatMessageId()).isEqualTo(firstSavedMessage.getId());
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
        assertThat(result.userId()).isEqualTo(chatRoom.getForeignerId());
    }

    @DisplayName("채팅방 페스워드를 생성하고 이에 대해 다시 채팅방에 입장하여 넣은 값에 대해 true를 리턴할 수 있다.")
    @Test
    void createPassword() {
        // given
        ChatRoom chatRoom = ChatFixture.chatRoom();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        ChatRoomPasswordCreateParam request = ChatFixture.chatRoomPasswordCreateRequest(savedChatRoom.getChatRoomId());
        chatService.createChatRoomPassword(request);
        ChatRoomPasswordCheckParam chatRoomPasswordCheckParam = ChatFixture.chatRoomPasswordCheckRequest(savedChatRoom.getChatRoomId());

        //when
        boolean isAuthorized = chatService.checkChatRoomPassword(chatRoomPasswordCheckParam);

        // then
        assertThat(isAuthorized).isTrue();
    }

    @DisplayName("채팅방에 입장하면 읽지 않은 상대방이 보낸 메세지가 읽음 처리로 된다.")
    @Test
    void enterChatRoom() {
        // given
        chatMessageRepository.deleteAll();

        Long chatRoomId = 1L;
        ChatMessage chatMessage = ChatFixture.firstReadNotChatMessageByFC(chatRoomId);
        chatMessageRepository.save(chatMessage);

        // when
        chatService.enterChatRoom(ChatFixture.chatRoomEnterParamByForeigner(chatRoomId));
        ChatMessageGetRepositoryResponses chatMessageByCursor
            = chatMessageRepository.getChatMessageByCursor(ChatFixture.chatMessageGetRepositoryRequest(chatRoomId));

        // then
        chatMessageByCursor.chatMessages()
            .stream().filter(ChatMessage::getIsFC)
            .forEach(chaMessageByFC -> assertThat(chaMessageByFC.getReadOrNot()).isTrue());
    }

    @DisplayName("외국인의 채팅방 목록을 불러오면 각 방 별 최신 메세지와 내가 읽지 않은 메세지의 수, 생성일을 확인할 수 있다.")
    @Test
    void getChatRoomInfoOForeigner() {
        // given
        chatMessageRepository.deleteAll();

        FinancialConsultant financialConsultant = financialConsultantRepository.save(FinancialConsultantFixture.financialConsultant());
        ChatRoom savedChatRoom = chatRoomRepository.save(ChatFixture.chatRoomByFcId(financialConsultant.getFinancialConsultantId()));

        Long chatRoomId = savedChatRoom.getChatRoomId();
        ChatMessage firstChatMessage = ChatFixture.firstReadNotChatMessageByFC(chatRoomId);
        chatMessageRepository.save(firstChatMessage);
        ChatMessage secondChatMessage = ChatFixture.secondReadNotChatMessageByFC(chatRoomId);
        chatMessageRepository.save(secondChatMessage);

        // when
        ChatRoomInfoOfForeignerResults chatRoomInfoOfForeigner = chatService.getChatRoomInfoOfForeigner(ChatFixture.chatRoomGetParamOfForeigner());
        ChatRoomInfoOfForeignerResult chatRoomInfoOfForeignerResult = chatRoomInfoOfForeigner.chatRoomInfoOfConsultantResults().get(0);

        // then
        assertThat(chatRoomInfoOfForeignerResult.chatRoomId()).isEqualTo(chatRoomId);
        assertThat(chatRoomInfoOfForeignerResult.contents()).isEqualTo(secondChatMessage.getContents());
        assertThat(chatRoomInfoOfForeignerResult.notReadCount()).isEqualTo(2);
        assertThat(chatRoomInfoOfForeignerResult.consultantName()).isEqualTo(financialConsultant.getName());
    }

    @DisplayName("상담원의 채팅방 목록을 불러오면 각 방 별 최신 메세지와 내가 읽지 않은 메세지의 수, 생성일을 확인할 수 있다.")
    @Test
    void getChatRoomInfoOfConsultant() {
        // given
        chatMessageRepository.deleteAll();

        Foreigner foreigner = foreignRepository.save(ForeignFixture.foreigner());
        ChatRoom savedChatRoom = chatRoomRepository.save(ChatFixture.chatRoomByForeignerId(foreigner.getForeignerId()));

        Long chatRoomId = savedChatRoom.getChatRoomId();
        ChatMessage firstChatMessage = ChatFixture.firstReadNotChatMessageByFC(chatRoomId);
        chatMessageRepository.save(firstChatMessage);
        ChatMessage secondChatMessage = ChatFixture.secondReadNotChatMessageByFC(chatRoomId);
        chatMessageRepository.save(secondChatMessage);

        // when
        ChatRoomInfoOfConsultantResults chatRoomInfoOfConsultant = chatService.getChatRoomInfoOfConsultant(ChatFixture.chatRoomGetParamOfConsultant());
        ChatRoomInfoOfConsultantResult chatRoomInfoOfConsultantResult = chatRoomInfoOfConsultant.chatRoomInfoOfConsultantResults().get(0);

        // then
        assertThat(chatRoomInfoOfConsultantResult.chatRoomId()).isEqualTo(chatRoomId);
        assertThat(chatRoomInfoOfConsultantResult.contents()).isEqualTo(secondChatMessage.getContents());
        assertThat(chatRoomInfoOfConsultantResult.notReadCount()).isEqualTo(0);
        assertThat(chatRoomInfoOfConsultantResult.foreignerName()).isEqualTo(foreigner.getName());
    }

    @DisplayName("채팅방에 나갈 때 관련 session 정보와 entry 정보가 함께 삭제된다.")
    @Test
    void leaveChatRoom() {
        // given
        String sessionId = "123ded";
        chatRoomEntryRepository.addMemberToRoom(ChatFixture.chatRoomEntryAddRepositoryRequestByForeigner(sessionId));
        chatSessionRepository.addSession(ChatFixture.chatSessionAddRepositoryRequestByForeigner(sessionId));
        chatSessionRepository.changeChatRoomEntry(ChatFixture.chatSessionChangeRepositoryRequest(sessionId));

        // when
        chatService.leaveChatRoom(ChatFixture.chatRoomLeaveParamByForeigner());

        // then
        assertThrows(IllegalArgumentException.class,
            () -> chatSessionRepository.getChatSession(sessionId));
        assertThrows(IllegalArgumentException.class,
            () -> chatRoomEntryRepository.getSession(ChatFixture.chatRoomEntryGetSessionRequest()));
    }

}
