package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.*;
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
                .param("isFC", "true")
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
                        parameterWithName("nextChatMessageId").description("무한스크롤 검색을 위한 다음 채팅메시지 ID"),
                        parameterWithName("isFC").description("설계사인지 여부")
                    ),
                    responseFields(
                        fieldWithPath("chatMessages[].chatMessageId").type(JsonFieldType.STRING).description("메세지 아이디"),
                        fieldWithPath("chatMessages[].contents").type(JsonFieldType.STRING).description("메세지 내용"),
                        fieldWithPath("chatMessages[].createdAt").type(JsonFieldType.STRING).description("생성 일자"),
                        fieldWithPath("chatMessages[].isFC").type(JsonFieldType.BOOLEAN).description("설계사가 보낸 메세지인지 고객인 보낸 메세지인지"),
                        fieldWithPath("chatMessages[].readOrNot").type(JsonFieldType.BOOLEAN).description("채팅메세지 읽음 여부"),
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

    @DisplayName("채팅방 패스워드를 만드는 API")
    @Test
    void createChatRoomPassword() throws Exception {
        mockMvc.perform(post("/chat-rooms/{chatRoomId}/passwords",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ChatFixture.chatRoomPasswordCreateRequest()))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-chat-room-password",
                pathParameters(
                    parameterWithName("chatRoomId").description("채팅방 id")
                ),
                requestFields(
                    fieldWithPath("chatRoomPassword").type(JsonFieldType.STRING).description("채팅방 password")
                )
            ));
    }

    @DisplayName("채팅방 패스워드를 체크하는 API")
    @Test
    void checkChatRoomPassword() throws Exception {
        given(chatService.checkChatRoomPassword(any())).willReturn(true);

        mockMvc.perform(patch("/chat-rooms/{chatRoomId}/passwords", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(ChatFixture.chatRoomPasswordCheckRequest()))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("check-chat-room-password",
                    pathParameters(
                        parameterWithName("chatRoomId").description("채팅방 id")
                    ),
                    responseFields(
                        fieldWithPath("isMyUser").type(JsonFieldType.BOOLEAN).description("해당 유저의 채팅방인가?")
                    )
                )
            );
    }

    @DisplayName("채팅방에 입장하는 API")
    @Test
    void enterChatRoom() throws Exception {
        mockMvc.perform(patch("/chat-rooms/{chatRoomId}/messages", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(ChatFixture.chatRoomEnterRequest()))
            )
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("enter-chat-room",
                    pathParameters(
                        parameterWithName("chatRoomId").description("채팅방 id")
                    ),
                    requestFields(
                        fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("입장한 유저가 상담원인가?")
                    )
                )
            );
    }

    @DisplayName("상담원의 채팅방 목록을 조회하는 API")
    @Test
    void getChatRoomInfoOfConsultant() throws Exception {
        ChatRoomInfoOfConsultantResults chatRoomInfoResults = ChatFixture.chatRoomInfoOfConsultantResults();
        given(chatService.getChatRoomInfoOfConsultant(any())).willReturn(chatRoomInfoResults);

        mockMvc.perform(get("/chat-rooms/consultants")
                .param("userId", String.valueOf(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-chat-room-info-of-consultant",
                    queryParameters(
                        parameterWithName("userId").description("유저 ID")
                    ),
                    responseFields(
                        fieldWithPath("responses[].chatRoomId").type(JsonFieldType.NUMBER).description("채팅방 ID"),
                        fieldWithPath("responses[].contents").type(JsonFieldType.STRING).description("해당 채팅방의 가장 최신 메세지 내용"),
                        fieldWithPath("responses[].messageCreatedAt").type(JsonFieldType.STRING).description("해당 채팅방의 가장 최신 메세지 생성 일자"),
                        fieldWithPath("responses[].notReadCount").type(JsonFieldType.NUMBER).description("채팅방의 내가 읽지 않은 메세지 개수"),
                        fieldWithPath("responses[].foreignerName").type(JsonFieldType.STRING).description("외국 고객님의 이름"),
                        fieldWithPath("responses[].chatRoomCreatedAt").type(JsonFieldType.STRING).description("채팅방 생성일자 - 생성일자 최신순으로 정렬되어 나옴")
                )
            ));
    }

    @DisplayName("외국인 고객의 채팅방 목록을 조회하는 API")
    @Test
    void getChatRoomInfoOfForeigner() throws Exception {
        ChatRoomInfoOfForeignerResults chatRoomInfoResults = ChatFixture.chatRoomInfoOfForeignerResults();
        given(chatService.getChatRoomInfoOfForeigner(any())).willReturn(chatRoomInfoResults);

        mockMvc.perform(get("/chat-rooms/foreigners")
                .param("userId", String.valueOf(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-chat-room-info-of-foreigner",
                queryParameters(
                    parameterWithName("userId").description("유저 ID")
                ),
                responseFields(
                    fieldWithPath("responses[].chatRoomId").type(JsonFieldType.NUMBER).description("채팅방 ID"),
                    fieldWithPath("responses[].contents").type(JsonFieldType.STRING).description("해당 채팅방의 가장 최신 메세지 내용"),
                    fieldWithPath("responses[].messageCreatedAt").type(JsonFieldType.STRING).description("해당 채팅방의 가장 최신 메세지 생성 일자"),
                    fieldWithPath("responses[].notReadCount").type(JsonFieldType.NUMBER).description("채팅방의 내가 읽지 않은 메세지 개수"),
                    fieldWithPath("responses[].consultantName").type(JsonFieldType.STRING).description("상담원의 이름"),
                    fieldWithPath("responses[].profileUrl").type(JsonFieldType.STRING).description("상담원 프로필")
                )
            ));
    }

    @DisplayName("채팅방을 나가는 API")
    @Test
    void leaveChatRoom() throws Exception {
        mockMvc.perform(patch("/chat-rooms/{chatRoomId}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(ChatFixture.chatRoomLeaveRequest()))
            )
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("leave-chat-room",
                pathParameters(
                    parameterWithName("chatRoomId").description("채팅방 id")
                ),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원의 Id"),
                    fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("떠나는 유저가 상담원인가?")
                )
            ));
    }

}
