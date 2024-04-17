package com.hellomeritz.member.domain;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ForeignerTest {

    @DisplayName("userId가 음수 또는 0이면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void testMinusOrZeroUserId(long userId) {
        assertThrows(IllegalArgumentException.class,
                () -> Foreigner.of(
                        userId,
                        "06-00-00-00-00-00",
                        SourceLanguage.미국,
                        VisaType.E1,
                        true,
                        LocalDate.of(1997, 1, 21)
                ));
    }

    @DisplayName("sourceLanguage이 null 혹은 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void testNullSourceLang(SourceLanguage sourceLanguage) {
        assertThrows(IllegalArgumentException.class,
                () -> Foreigner.of(
                        1L,
                        "06-00-00-00-00-00",
                        sourceLanguage,
                        VisaType.E1,
                        true,
                        LocalDate.of(1997, 1, 21)
                ));
    }

    @DisplayName("visaType이 null 혹은 빈값이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void testNullVisaType(VisaType visaType) {
        assertThrows(IllegalArgumentException.class,
                () -> Foreigner.of(
                        1L,
                        "06-00-00-00-00-00",
                        SourceLanguage.미국,
                        visaType,
                        true,
                        LocalDate.of(1997, 1, 21)
                ));
    }

    @DisplayName("birthDate의 범위가 최대 범위를 벗어나면 예외를 던진다.")
    @Test
    void testExceedMaxBirthDate( ) {
        assertThrows(IllegalArgumentException.class,
                () -> Foreigner.of(
                        1L,
                        "06-00-00-00-00-00",
                        SourceLanguage.미국,
                        VisaType.E1,
                        true,
                        LocalDate.now().plusDays(7)
                ));
    }

    @DisplayName("birthDate의 범위가 최소 범위보다 작으면 예외를 던진다.")
    @Test
    void testExceedMinBirthDate( ) {
        assertThrows(IllegalArgumentException.class,
                () -> Foreigner.of(
                        1L,
                        "06-00-00-00-00-00",
                        SourceLanguage.미국,
                        VisaType.E1,
                        true,
                        LocalDate.of(1799,1,1)
                ));
    }

}
