package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.global.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ChatControllerTest extends RestDocsSupport {

    ChatService chatService = mock(ChatService.class);

    @Override
    protected Object initController() {
        return new ChatController(chatService);
    }

    @DisplayName("채팅메세지를 무한스크롤로 확인하는 API")
    @Test
    void getChatMessages() {


    }
}