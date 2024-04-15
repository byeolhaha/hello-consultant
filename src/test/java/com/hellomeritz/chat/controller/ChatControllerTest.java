package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatControllerTest extends ControllerTestSupport {

    @DisplayName("audioUrl이 빈값인 경우 검증한다.")
    @ParameterizedTest
    @NullSource
    void sendSttMessage_nullOrEmpty_audioUrl(String audioUrl) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}/stt", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageSttRequest(
                                audioUrl,
                                1L,
                                true,
                                "EN"
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("userId가 음수 또는 0인 경우 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void sendSttMessage_minusOrZero_userId(long userId) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}/stt", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageSttRequest(
                                "gs://meritz.com/uuid",
                                userId,
                                true,
                                "EN"
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("audioUrl이 빈값인 경우 검증한다.")
    @ParameterizedTest
    @NullSource
    void sendSttMessage_nullOrEmpty_sourceLang(String sourceLang) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}/stt", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageSttRequest(
                                "gs://meritz.com/uuid",
                                1L,
                                true,
                                sourceLang
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("chatRoomId가 음수 혹은 0인 경우 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void sendSttMessage_minusOrZero_chatRoomId(long chatRoomId) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}/stt", chatRoomId)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageSttRequest(
                                "gs://meritz.com/uuid",
                                1L,
                                true,
                                "EN"
                        )
                ))).andExpect(status().is4xxClientError());
    }

}
