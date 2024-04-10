package com.hellomeritz.chat.service;

import com.hellomeritz.chat.repository.ChatMessageRepository;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateTextResult;
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

}