package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hellomeritz.chat.global.TargetLanguage;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatControllerTest extends ControllerTestSupport {

    @DisplayName("isFC는 null값인 경우 예외를 던진다.")
    @Test
    void sendSttMessage_nullOrEmpty_audioUrl() throws Exception {
        ChatMessageSttRequest request = new ChatMessageSttRequest(
            1L,
            null,
            "US"
        );
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

        mockMvc.perform(multipart("/chat/{chatRoomId}/stt", 1L)
            .file(mockAudioFile)
            .file(mockRequest)
            .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

    @DisplayName("userId가 음수 또는 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void sendSttMessage_minusOrZero_userId(long userId) throws Exception {
        ChatMessageSttRequest request = new ChatMessageSttRequest(
            userId,
            true,
            "US"
        );

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

        mockMvc.perform(multipart("/chat/{chatRoomId}/stt", 1L)
            .file(mockAudioFile)
            .file(mockRequest)
            .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

    @DisplayName("sourceLang이 빈값인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void sendSttMessage_nullOrEmpty_sourceLang(String sourceLang) throws Exception {
        ChatMessageSttRequest request = new ChatMessageSttRequest(
            1L,
            true,
            sourceLang
        );

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

        mockMvc.perform(multipart("/chat/{chatRoomId}/stt", 1L)
            .file(mockAudioFile)
            .file(mockRequest)
            .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

    @DisplayName("sourceLang이 Enum 형식에 맞니 경우 예외를 던진다.")
    @Test
    void sendSttMessage_notMeetFormat_sourceLang() throws Exception {
        ChatMessageSttRequest request = new ChatMessageSttRequest(
            1L,
            true,
            "US는 미국인데?"
        );

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

        mockMvc.perform(multipart("/chat/{chatRoomId}/stt", 1L)
            .file(mockAudioFile)
            .file(mockRequest)
            .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

    @DisplayName("chatRoomId가 음수 혹은 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void sendSttMessage_minusOrZero_chatRoomId(long chatRoomId) throws Exception {
        ChatMessageSttRequest request = ChatFixture.chatAudioUploadRequest();
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

        mockMvc.perform(multipart("/chat/{chatRoomId}/stt", chatRoomId)
            .file(mockAudioFile)
            .file(mockRequest)
            .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError());
    }

    @DisplayName("chatRoomId가 음수 혹은 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void sendMessage_minusOrZero_chatRoomId(long chatRoomId) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}", chatRoomId)
            .content(objectMapper.writeValueAsString(
                new ChatMessageTranslateRequest(
                    "안녕하세요",
                    1L,
                    true,
                    "EN",
                    "KO"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("contents가 빈값인 경우를 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void sendMessage_nullOrEmpty_contents(String contents) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ChatMessageTranslateRequest(
                    contents,
                    1L,
                    true,
                    "EN",
                    "KO"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("targetLang가 빈값인 경우를 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void sendMessage_nullOrEmpty_targetLang(String targetLang) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ChatMessageTranslateRequest(
                    "안녕하세요",
                    1L,
                    true,
                    targetLang,
                    "KO"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("targetLang이 enum 형식에 맞지 않는 경우 예외를 던진다.")
    @Test
    void sendMessage_notMeetFormat_targetLang() throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ChatMessageTranslateRequest(
                    "안녕하세요",
                    1L,
                    true,
                    TargetLanguage.findTargetLanguage("UK") + "메롱",
                    "KO"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("sourceLang가 빈값인 경우를 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void sendMessage_nullOrEmpty_sourceLang(String sourceLang) throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ChatMessageTranslateRequest(
                    "안녕하세요",
                    1L,
                    true,
                    "EN",
                    sourceLang
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("sourceLang가 enum 형식에 맞지 앉는 경우 예외를 던진다.")
    @Test
    void sendMessage_notMeetFormat_sourceLang() throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ChatMessageTranslateRequest(
                    "안녕하세요",
                    1L,
                    true,
                    "EN",
                    "UK"
                )
            ))).andExpect(status().is4xxClientError());
    }

}
