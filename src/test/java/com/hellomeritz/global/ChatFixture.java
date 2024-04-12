package com.hellomeritz.global;

import com.hellomeritz.chat.controller.dto.request.ChatAudioUploadRequest;
import com.hellomeritz.chat.controller.dto.response.ChatMessageGetResponses;
import com.hellomeritz.chat.domain.ChatMessage;
import com.hellomeritz.chat.domain.ChatMessageType;
import com.hellomeritz.chat.service.dto.param.ChatMessageGetParam;
import com.hellomeritz.chat.service.dto.param.ChatMessageTextParam;
import com.hellomeritz.chat.service.dto.param.ChatRoomCreateParam;
import com.hellomeritz.chat.service.dto.result.ChatMessageGetResults;

import java.time.LocalDateTime;
import java.util.List;

public class ChatFixture {

    private static final long USER_ID = 1;
    private static final long FC_ID = 2;
    private static final String CHAT_MESSAGE_ID_MIN_VALUE = "000000000000000000000000";

    private ChatFixture() {
        throw new AssertionError("생성자를 통해서 인스턴스를 생성할 수 없습니다.");
    }

    public static ChatMessageTextParam chatMessageTextParamByFC() {
        return new ChatMessageTextParam(
                "안녕하세요 반가워요",
                FC_ID,
                true,
                1,
                "EN",
                "KO"
        );
    }

    public static ChatMessage originChatMessageByFC() {
        return ChatMessage.of(
                "안녕하세요 반가워요",
                ChatMessageType.TEXT.name(),
                FC_ID,
                true,
                1
        );
    }

    public static ChatMessage translatedChatMessageByFC() {
        return ChatMessage.of(
                "hello nice meet you",
                ChatMessageType.TRANSLATED_TEXT.name(),
                FC_ID,
                true,
                1
        );
    }

    public static ChatMessage originChatMessageByUser() {
        return ChatMessage.of(
                "Hello my name is byeol",
                ChatMessageType.TEXT.name(),
                FC_ID,
                true,
                1
        );
    }

    public static ChatMessage translatedChatMessageByUser() {
        return ChatMessage.of(
                "안녕하세요 저의 이름은 별입니다.",
                ChatMessageType.TRANSLATED_TEXT.name(),
                FC_ID,
                true,
                1
        );
    }

    public static ChatMessageTextParam chatMessageTextParamByUser() {
        return new ChatMessageTextParam(
                "Hello my name is byeol",
                USER_ID,
                false,
                1,
                "KO",
                "EN"
        );
    }

    public static ChatRoomCreateParam chatRoomCreateParam() {
        return new ChatRoomCreateParam(
                1L,
                1L
        );
    }

    public static ChatMessageGetParam chatMessageGetParam() {
        return new ChatMessageGetParam(
                FC_ID,
                CHAT_MESSAGE_ID_MIN_VALUE,
                1
        );
    }

    public static ChatMessageGetResults chatMessageGetResults() {
        return new ChatMessageGetResults(
                List.of(
                        new ChatMessageGetResults.ChatMessageGetResult(
                                "66172f7ab156dc2cf99c4b2c",
                                "안녕하세요 반갑습니다.",
                                LocalDateTime.of(2024, 3, 1, 11, 20).toString(),
                                true
                        ),
                        new ChatMessageGetResults.ChatMessageGetResult(
                                "66172f7ab156dc2cf99c4b2d",
                                "hello nice meet you",
                                LocalDateTime.of(2024, 3, 1, 11, 21).toString(),
                                true
                        ),
                        new ChatMessageGetResults.ChatMessageGetResult(
                                "66172f7ab156dc2cf99c4b2e",
                                "hello my name is byeol",
                                LocalDateTime.of(2024, 3, 1, 11, 22).toString(),
                                false
                        ),
                        new ChatMessageGetResults.ChatMessageGetResult(
                                "66172f7ab156dc2cf99c4b2f",
                                "안녕하세요 제 이름은 별입니다.",
                                LocalDateTime.of(2024, 3, 1, 11, 23).toString(),
                                true
                        )
                ),
                "66172f7ab156dc2cf99c4b2c",
                true
        );
    }

    public static ChatAudioUploadRequest chatAudioUploadRequest() {
        return new ChatAudioUploadRequest(1L, true);
    }

}
