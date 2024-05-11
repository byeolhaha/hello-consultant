package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.global.stt.SttProvider;
import com.hellomeritz.chat.service.ChatMessageService;
import com.hellomeritz.chat.service.dto.result.ChatMessageSttResult;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatControllerDocsTest extends RestDocsSupport {

    ChatMessageService chatMessageService = mock(ChatMessageService.class);

    @Override
    protected Object initController() {
        return new ChatController(chatMessageService);
    }

    @DisplayName("음성 파일을 보내면 TEXT로 바꿔주는 STT API")
    @Test
    void sendAudioMessage() throws Exception {
        ChatMessageSttResult result = ChatFixture.chatMessageSttResult();
        given(chatMessageService.sendAudioMessage(any())).willReturn(result);

        ChatMessageSttRequest request = ChatFixture.chatAudioUploadRequest();
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

        mockMvc.perform(multipart("/chats/{chatRoomId}/stt", 1L)
                .file(mockRequest)
                .file(mockAudioFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("change-audiofile-to-text",
                pathParameters(
                    parameterWithName("chatRoomId").description("채팅방 id")
                ),
                requestPartFields("request",
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("음성 파일 업로드를 요청하는 user ID"),
                    fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("해당 유저가 설계사인지 여부"),
                    fieldWithPath("sourceLang").type(JsonFieldType.STRING).description("외국인의 소통 가능한 언어")
                ),
                requestParts(
                    partWithName("request").description("json 형식으로 위 part 필드들에 대해 요청해주시면 됩니다."),
                    partWithName("audioFile").description("올리는 음성 파일 mp3, wav")
                ),
                responseFields(
                    fieldWithPath("textBySpeech").type(JsonFieldType.STRING).description("audio가 업로드되고 난 후 이동할 수 있는 링크"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("해당 파일이 저장된 일자"),
                    fieldWithPath("sttProvider").type(JsonFieldType.STRING).description("해당 stt 출처 : "+
                        Arrays.stream(SttProvider.values())
                            .map(Enum::name)
                        .collect(Collectors.joining(", "))))
                )
            );
    }

}
