package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageGetRequest;
import com.hellomeritz.chat.controller.dto.request.ChatRoomCreateRequest;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatRoomControllerTest extends ControllerTestSupport {

    @DisplayName("myId가 null이거나 빈값인 경우를 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void getChatMessage_nullOrEmpty_myId(Long myId) throws Exception {
        mockMvc.perform(get("/chat-rooms/{chtRoomId}/messages", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageGetRequest(
                                myId,
                                "",
                                true
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("myId가 0이거나 음수인 경우를 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getChatMessage_invalid_myId(Long myId) throws Exception {
        mockMvc.perform(get("/chat-rooms/{chtRoomId}/messages", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageGetRequest(
                                myId,
                                "",
                                true
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("chatRoomId가 0이거나 음수인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getChatMessage_invalid_chatRoomId(Long chatRoomId) throws Exception {
        mockMvc.perform(get("/chat-rooms/{chtRoomId}/messages", chatRoomId)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageGetRequest(
                                1L,
                                "",
                                true
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("fcId가 null이거나 빈값인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void getChatMessage_nullOrEmpty_fcId(Long fcId) throws Exception {
        mockMvc.perform(post("/chat-rooms")
                .content(objectMapper.writeValueAsString(
                        new ChatRoomCreateRequest(
                                fcId,
                                1L
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("fcId가 0이거나 음수인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getChatMessage_invalid_fcId(Long fcId) throws Exception {
        mockMvc.perform(post("/chat-rooms")
                .content(objectMapper.writeValueAsString(
                        new ChatRoomCreateRequest(
                                fcId,
                                1L
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("userId가 null이거나 빈값인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void getChatMessage_nullOrEmpty_userId(Long userId) throws Exception {
        mockMvc.perform(post("/chat-rooms")
                .content(objectMapper.writeValueAsString(
                        new ChatRoomCreateRequest(
                                1L,
                                userId
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("fcId가 0이거나 음수인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getChatMessage_invalid_userId(Long userId) throws Exception {
        mockMvc.perform(post("/chat-rooms")
                .content(objectMapper.writeValueAsString(
                        new ChatRoomCreateRequest(
                                1L,
                                userId
                        )
                ))).andExpect(status().is4xxClientError());
    }

}
