package com.hellomeritz.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hellomeritz.chat.controller.dto.request.ChatAudioUploadRequest;
import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.ChatAudioUploadResult;
import com.hellomeritz.chat.service.dto.result.ChatMessageSttResult;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.RestDocsSupport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatControllerDocsTest extends RestDocsSupport {

    ChatService chatService = mock(ChatService.class);

    @Override
    protected Object initController() {
        return new ChatController(chatService);
    }

    @DisplayName("음성 파일을 보내는 API")
    @Test
    void uploadAudioFile() throws Exception {
        ChatAudioUploadResult result = ChatFixture.chatAudioUploadResult();
        given(chatService.uploadAudioFile(any())).willReturn(result);

        ChatAudioUploadRequest request = ChatFixture.chatAudioUploadRequest();
        MockMultipartFile mockRequest = new MockMultipartFile(
                "request",
                "content",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile mockAudioFile = new MockMultipartFile(
                "audioFile",
                "audio.mp3",
                null,
                "audio-files".getBytes()
        );

        mockMvc.perform(multipart("/chats/{chatRoomId}/audios", 1L)
                        .file(mockRequest)
                        .file(mockAudioFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("upload-audios",
                        pathParameters(
                                parameterWithName("chatRoomId").description("채팅방 id")
                        ),
                        requestPartFields("request",
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("음성 파일 업로드를 요청하는 user ID"),
                                fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("해당 유저가 설계사인지 여부")
                        ),
                        requestParts(
                                partWithName("request").description("json 형식으로 위 part 필드들에 대해 요청해주시면 됩니다."),
                                partWithName("audioFile").description("올리는 음성 파일 mp3, wav")
                        ),
                        responseFields(
                                fieldWithPath("audioUrl").type(JsonFieldType.STRING).description("audio가 업로드되고 난 후 이동할 수 있는 링크"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("해당 파일이 저장된 일자")
                        )
                ));
    }

    @DisplayName("음성 파일을 text로 응답하는 API")
    @Test
    void sendAudioText() throws Exception {
        ChatMessageSttResult result = ChatFixture.chatMessageSttResult();
        given(chatService.sendAudioMessage(any())).willReturn(result);

        mockMvc.perform(post("/chats/{chatRoomId}/stt", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChatFixture.chatMessageSttRequest()))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("change-audiofile-to-text",
                        requestFields(
                                fieldWithPath("audioUrl").type(JsonFieldType.STRING).description("audio Url"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("설계사 여부"),
                                fieldWithPath("sourceLang").type(JsonFieldType.STRING).description("audio file의 해당 언어")
                        ),
                        responseFields(
                                fieldWithPath("textBySpeech").type(JsonFieldType.STRING).description("음성 파일에서 TEXT로 반환된 내용"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                        ),
                        pathParameters(
                                parameterWithName("chatRoomId").description("채팅방 id")
                        )
                ));
    }

    @DisplayName("text를 원하는 언어로 번역하는 API")
    @Test
    void sendChatMessageToTranslate() throws Exception {
        ChatMessageTranslateResult result = ChatFixture.chatMessageTranslateResult();
        given(chatService.translateText(any())).willReturn(result);

        mockMvc.perform(post("/chats/{chatRoomId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChatFixture.chatMessageTranslateRequest()))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("translate-text",
                        requestFields(
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("채팅을 통해 보낸 text"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("설계사 여부"),
                                fieldWithPath("targetLang").type(JsonFieldType.STRING).description("번역되고자 하는 언어"),
                                fieldWithPath("sourceLang").type(JsonFieldType.STRING).description("사용자가 보낸 text의 해당 언어")
                        ),
                        responseFields(
                                fieldWithPath("originContents").type(JsonFieldType.STRING).description("원본 text"),
                                fieldWithPath("translatedContents").type(JsonFieldType.STRING).description("해석된 text"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                        ),
                        pathParameters(
                                parameterWithName("chatRoomId").description("채팅방 id")
                        )
                ));
    }

}
