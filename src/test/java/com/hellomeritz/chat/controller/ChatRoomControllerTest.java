package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatRoomControllerTest extends RestDocsSupport {

    ChatService chatService = mock(ChatService.class);

    @Override
    protected Object initController() {
        return new ChatController(chatService);
    }

    @DisplayName("채팅메세지를 무한스크롤로 확인하는 API")
    @Test
    void getChatMessages() throws Exception{
        Long chatRoomId = 1L;
        ChatMessageGetResults results = ChatFixture.chatMessageGetResults();
        given(chatService.getChatMessages(any())).willReturn(results);

        mockMvc.perform(get("/chat-rooms/{chatRoomId}",chatRoomId)
                .param("myId",String.valueOf(1))
                .param("nextChatMessageId","66172f7ab156dc2cf99c4b2c"))
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
                                fieldWithPath("chatMessageGetResponses[].contents").type(JsonFieldType.NUMBER).description("메세지 아이디"),
                                fieldWithPath("chatMessageGetResponses[].createdAt").type(JsonFieldType.NUMBER).description("보낸사람 아이디"),
                                fieldWithPath("chatMessageGetResponses[].isMine").type(JsonFieldType.STRING).description("메세지 내용"),
                                fieldWithPath("nextChatMessageId").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"))
                        )
                        );

    }
}


