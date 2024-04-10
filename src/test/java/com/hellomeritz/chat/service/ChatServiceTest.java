package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.repository.chatmessage.ChatMessageRepository;
import com.hellomeritz.chat.repository.chatroom.ChatRoomRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.param.ChatRoomCreateParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @DisplayName("채팅메세지를 저장할 수 있다.")
    @Test
    void saveChatMessage() {
        // given
        ChatMessageTextParam chatMessageTextParam = ChatFixture.chatMessageTextParam();

        // when
        ChatMessageTranslateTextResult result = chatService.translateText(chatMessageTextParam);

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

}