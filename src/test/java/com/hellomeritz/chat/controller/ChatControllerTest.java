package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.TargetLanguage;
import com.hellomeritz.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatControllerTest extends ControllerTestSupport {

    @DisplayName("audioUrl이 빈값인 경우 예외를 던진다.")
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

    @DisplayName("userId가 음수 또는 0인 경우 예외를 던진다.")
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

    @DisplayName("sourceLang이 빈값인 경우 예외를 던진다.")
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

    @DisplayName("sourceLang이 Enum 형식에 맞니 경우 예외를 던진다.")
    @Test
    void sendSttMessage_notMeetFormat_sourceLang() throws Exception {
        mockMvc.perform(post("/chats/{chtRoomId}/stt", 1L)
                .content(objectMapper.writeValueAsString(
                        new ChatMessageSttRequest(
                                "gs://meritz.com/uuid",
                                1L,
                                true,
                                SourceLanguage.미국.getGoogleSttLang() + "메롱"
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("chatRoomId가 음수 혹은 0인 경우 예외를 던진다.")
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
                                TargetLanguage.미국.getDeeplLang() + "메롱",
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
                                SourceLanguage.미국.getDeeplLang()
                        )
                ))).andExpect(status().is4xxClientError());
    }

}
