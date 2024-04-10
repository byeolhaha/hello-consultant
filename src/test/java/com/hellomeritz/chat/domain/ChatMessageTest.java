package com.hellomeritz.chat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @DisplayName("contents가 null 혹은 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void testNullOrEmptyContents(String contents) {
        assertThrows(IllegalArgumentException.class,
            () -> new ChatMessage(
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
            () -> new ChatMessage(
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
            () -> new ChatMessage(
                contentsOverLength,
                ChatMessageType.TEXT.name(),
                1,
                true,
                1
            ));
    }

    static Stream<String> provideInvalidLength() {
        return Stream.of(generateStringWithLength(102));
    }

    private static String generateStringWithLength(int length) {
        return "a".repeat(Math.max(0, length));
    }

}