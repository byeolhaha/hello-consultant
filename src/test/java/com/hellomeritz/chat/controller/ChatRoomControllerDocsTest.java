package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;
import com.hellomeritz.chat.service.dto.result.ChatRoomCreateResult;
import com.hellomeritz.chat.service.dto.result.ChatRoomUserInfoResult;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatRoomControllerDocsTest extends RestDocsSupport {

    ChatService chatService = mock(ChatService.class);

    @Override
    protected Object initController() {
        return new ChatRoomController(chatService);
    }

    @DisplayName("채팅메세지를 무한스크롤로 확인하는 API")
    @Test
    void getChatMessages() throws Exception {
        Long chatRoomId = 1L;
        ChatMessageGetResults results = ChatFixture.chatMessageGetResults();
        given(chatService.getChatMessages(any())).willReturn(results);

        mockMvc.perform(get("/chat-rooms/{chatRoomId}/messages", 1L)
                        .param("myId", String.valueOf(1L))
                        .param("nextChatMessageId", "000000000000000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-chat-messages",
                                pathParameters(
                                        parameterWithName("chatRoomId").description("채팅방 id")
                                ),
                                queryParameters(
                                        parameterWithName("myId").description("유저 ID"),
                                        parameterWithName("nextChatMessageId").description("무한스크롤 검색을 위한 다음 채팅메시지 ID")
                                ),
                                responseFields(
                                        fieldWithPath("chatMessages[].chatMessageId").type(JsonFieldType.STRING).description("메세지 아이디"),
                                        fieldWithPath("chatMessages[].contents").type(JsonFieldType.STRING).description("메세지 내용"),
                                        fieldWithPath("chatMessages[].createdAt").type(JsonFieldType.STRING).description("생성 일자"),
                                        fieldWithPath("chatMessages[].isMine").type(JsonFieldType.BOOLEAN).description("내가 보낸 메세지 여부"),
                                        fieldWithPath("nextChatMessageId").type(JsonFieldType.STRING).description("다음 페이지를 불러오기 위한 nextKey 값"),
                                        fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"))
                        )
                );
    }

    @DisplayName("채팅방을 생성하는 API")
    @Test
    void createChatRoom() throws Exception {
        ChatRoomCreateResult result = ChatFixture.chatRoomCreateResult();
        given(chatService.createChatRoom(any())).willReturn(result);

        mockMvc.perform(post("/chat-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChatFixture.chatRoomCreateRequest()))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-chat-rooms",
                        requestFields(
                                fieldWithPath("fcId").type(JsonFieldType.NUMBER).description("설계사 ID"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("외국인 유저 ID")
                        ),
                        responseFields(
                                fieldWithPath("chatRoomId").type(JsonFieldType.NUMBER).description("생성된 채팅방 ID")
                        )
                ));
    }

    @DisplayName("채팅방의 유저 정보를 확인하는 API")
    @Test
    void getChatRoomUserInfo() throws Exception {
        Long chatRoomId = 1L;
        ChatRoomUserInfoResult result = ChatFixture.chatRoomUserInfoResult();
        given(chatService.getChatRoomUserInfo(any())).willReturn(result);

        mockMvc.perform(get("/chat-rooms/{chatRoomId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-chat-room-userInfo",
                                pathParameters(
                                        parameterWithName("chatRoomId").description("채팅방 id")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("외국인 유저 ID"),
                                        fieldWithPath("fcId").type(JsonFieldType.NUMBER).description("설계사 ID")
                                )
                        )
                );
    }

}


