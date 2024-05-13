package com.hello.member.controller;

import com.hello.global.ControllerTestSupport;
import com.hello.member.controller.dto.request.ForeignInfoSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends ControllerTestSupport {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    @DisplayName("language가 빈 값이나 null인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void saveForeignInfo_nullOrEmpty_language(String language) throws Exception {
        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    language,
                    "E1",
                    true,
                    "19970121",
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("language이 지원하는 언어가 아닌 경우 예외를 던진다.")
    @Test
    void saveForeignInfo_notExisted_language() throws Exception {
        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "de",
                    "E1",
                    true,
                    "19970121",
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("visaType이 빈 값이나 null인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void saveForeignInfo_nullOrEmpty_visaType(String visaType) throws Exception {
        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "en_US",
                    visaType,
                    true,
                    "19970121",
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("visaType에 존재하지 않는 값을 입력한 경우 예외를 던진다.")
    @Test
    void saveForeignInfo_notExisted_visaType() throws Exception {
        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "en_US",
                    "EE1",
                    true,
                    "19970121",
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("bithDate이 빈 값이나 null인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void saveForeignInfo_nullOrEmpty_bithDate(String birthDate) throws Exception {
        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "en_US",
                    "E1",
                    true,
                    birthDate,
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("bithDate이 형식이 올바르지 않는 경우 예외를 던진다.")
    @Test
    void saveForeignInfo_notMeetFormat_birthDate() throws Exception {
        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "en_US",
                    "E1",
                    true,
                    "1997-01-21",
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("bithDate의 값이 오늘을 넘는 경우 예외를 던진다.")
    @Test
    void saveForeignInfo_max_birthDate() throws Exception {

        mockMvc.perform(put("/users/{chtRoomId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "en_US",
                    "E1",
                    true,
                    LocalDate.now().plusDays(10).format(dateTimeFormatter),
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("bithDate의 값이 최소값보다 작은 경우 예외를 던진다.")
    @Test
    void saveForeignInfo_min_birthDate() throws Exception {

        mockMvc.perform(put("/users/{userId}", 1L)
            .content(objectMapper.writeValueAsString(
                new ForeignInfoSaveRequest(
                    "en_US",
                    "E1",
                    true,
                    "11111111",
                    "Jenie"
                )
            ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("설계사에게 알람을 보낼 때 chatRoomId가 음수 또는 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0"})
    void notifyForeignerArrival_minusOrZero_chatRoomId(String chatRoomId) throws Exception {

        mockMvc.perform(get("/users/{userId}/alarm", 1L)
            .param("chatRoomId", chatRoomId)
        ).andExpect(status().is4xxClientError());
    }

    @DisplayName("설계사에게 알람을 보낼 때 fcId가 음수 또는 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void notifyForeignerArrival_minusOrZero_fcId(long fcId) throws Exception {

        mockMvc.perform(get("/users/{userId}/alarm", fcId)
            .param("chatRoomId", "1")
        ).andExpect(status().is4xxClientError());
    }


}
