package com.hellomeritz.member.controller;

import com.hellomeritz.global.ControllerTestSupport;
import com.hellomeritz.member.controller.dto.ForeignInfoSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                                "19970121"
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
                                "19970121"
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
                                "19970121"
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
                                "19970121"
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
                                birthDate
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
                                "1997-01-21"
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
                                LocalDate.now().plusDays(10).format(dateTimeFormatter)
                        )
                ))).andExpect(status().is4xxClientError());
    }

    @DisplayName("bithDate의 값이 최소값보다 작은 경우 예외를 던진다.")
    @Test
    void saveForeignInfo_min_birthDate() throws Exception {

        mockMvc.perform(put("/users/{chtRoomId}", 1L)
                .content(objectMapper.writeValueAsString(
                        new ForeignInfoSaveRequest(
                                "en_US",
                                "E1",
                                true,
                                "11111111"
                        )
                ))).andExpect(status().is4xxClientError());
    }


}
