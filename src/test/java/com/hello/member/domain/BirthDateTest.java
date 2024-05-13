package com.hello.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @DisplayName("birthDate의 범위가 최대 범위를 벗어나면 예외를 던진다.")
    @Test
    void testExceedMaxBirthDate() {
        assertThrows(IllegalArgumentException.class,
                () -> BirthDate.of(LocalDate.now().plusDays(10).format(dateTimeFormatter)));
    }

    @DisplayName("birthDate의 범위가 최소 범위보다 작으면 예외를 던진다.")
    @Test
    void testExceedMinBirthDate() {
        assertThrows(IllegalArgumentException.class,
                () -> BirthDate.of("11111111"));
    }

    @DisplayName("birthDate가 형식에 맞지 않으면 예외를 던진다.")
    @Test
    void testNotMeetFormatBirthDate() {
        assertThrows(IllegalArgumentException.class,
                () -> BirthDate.of("1997-01-21"));
    }

}
