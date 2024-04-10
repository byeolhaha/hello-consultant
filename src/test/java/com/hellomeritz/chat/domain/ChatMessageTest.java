package com.hellomeritz.chat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @DisplayName("contents가 null 혹은 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void testNullOrEmptyContents(String contents) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatMessage.of(
                contents,
                ChatMessageType.TEXT.name(),
                1,
                true,
                1
            ));
    }

    @DisplayName("messageType이 null 혹은 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void testNullOrEmptyMessageType(String messageType) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatMessage.of(
                "안녕",
                messageType,
                1,
                true,
                1
            ));
    }

    @DisplayName("contents 길이가 최대 글자수를 넘어가면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("provideInvalidLength")
    void testMaxContentsLength(String contentsOverLength) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatMessage.of(
                contentsOverLength,
                ChatMessageType.TEXT.name(),
                1,
                true,
                1
            ));
    }

    @DisplayName("userId가 음수이거나 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void testInvalidUserId(long userId) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatMessage.of(
                "안녕하세요",
                ChatMessageType.TEXT.name(),
                userId,
                true,
                1
            ));
    }

    @DisplayName("chatRoomId가 음수이거나 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void testInvalidChatRoomId(long chatRoomId) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatMessage.of(
                "안녕하세요",
                ChatMessageType.TEXT.name(),
                1,
                true,
                chatRoomId
            ));
    }

    static Stream<String> provideInvalidLength() {
        return Stream.of(generateStringWithLength(102));
    }

    private static String generateStringWithLength(int length) {
        return "a".repeat(Math.max(0, length));
    }

}