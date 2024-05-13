package com.hello.chat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ChatRoomTest {

    @DisplayName("userId가 음수이거나 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void testInvalidUserId(long userId) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatRoom.of(
                1,
                userId
            ));
    }

    @DisplayName("fcId가 음수이거나 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void testInvalidFcId(long fcId) {
        assertThrows(IllegalArgumentException.class,
            () -> ChatRoom.of(
                fcId,
                1
            ));
    }

}