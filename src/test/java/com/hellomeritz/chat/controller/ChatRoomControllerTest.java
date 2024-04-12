package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatAudioUploadRequest;
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

    @DisplayName("myId가 null이거나 빈값인 경우를 검증한다.")
    @ParameterizedTest
    @NullSource
    void getChatMessage_nullOrEmpty_myId(Long myId) throws Exception {
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

    @DisplayName("chatRoomId가 0이거나 음수인 경우를 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getChatMessage_invalid_chatRoomId(Long chatRoomId) throws Exception {
        mockMvc.perform(get("/chat-rooms/{chtRoomId}/messages", chatRoomId)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageGetRequest(
                                1L,
                                ""
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("fcId가 null이거나 빈값인 경우를 검증한다.")
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

    @DisplayName("fcId가 0이거나 음수인 경우를 검증한다.")
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

    @DisplayName("userId가 null이거나 빈값인 경우를 검증한다.")
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

    @DisplayName("fcId가 0이거나 음수인 경우를 검증한다.")
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

    @DisplayName("userId가 0이거나 음수인 경우를 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void uploadAudio_userId(long userId) throws Exception {
        ChatAudioUploadRequest request = new ChatAudioUploadRequest(userId, true);
        MockMultipartFile mockRequest = new MockMultipartFile(
                "text",
                "content",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile mockAudioFile = new MockMultipartFile(
                "audio",
                "audio.mp3",
                "mp3",
                "audio-files".getBytes()
        );

        mockMvc.perform(multipart("/chat/{chatRoomId}/audio", 1L)
                .file(mockAudioFile)
                .file(mockRequest)
                .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

    @DisplayName("userId가 0이거나 음수인 경우를 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void uploadAudio_chatRoomId(long chatRoomId) throws Exception {
        ChatAudioUploadRequest request = ChatFixture.chatAudioUploadRequest();
        MockMultipartFile mockRequest = new MockMultipartFile(
                "text",
                "content",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile mockAudioFile = new MockMultipartFile(
                "audio",
                "audio.mp3",
                "mp3",
                "audio-files".getBytes()
        );

        mockMvc.perform(multipart("/chat/{chatRoomId}/audio", chatRoomId)
                .file(mockAudioFile)
                .file(mockRequest)
                .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

}
