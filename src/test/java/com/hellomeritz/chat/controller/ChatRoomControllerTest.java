package com.hellomeritz.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hellomeritz.chat.controller.dto.request.ChatMessageGetRequest;
import com.hellomeritz.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatRoomControllerTest extends ControllerTestSupport {

    @DisplayName("myId가 null이거나 빈값인 경우를 검증한다.")
    @ParameterizedTest
    @NullSource
    void getChatMessage_nullOrEmpty_MyId(Long myId) throws Exception {
        mockMvc.perform(get("/chat-rooms/{chtRoomId}/messages", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageGetRequest(
                                myId,
                                ""
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("myId가 0이거나 음수인 경우를 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getChatMessage_invalid_myId(Long myId) throws Exception {
        mockMvc.perform(get("/chat-rooms/{chtRoomId}/messages", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageGetRequest(
                                myId,
                                ""
                        )
                ))).andExpect(status().is4xxClientError());
    }


}
