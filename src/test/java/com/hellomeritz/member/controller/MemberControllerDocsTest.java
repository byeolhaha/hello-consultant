package com.hellomeritz.member.controller;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import com.hellomeritz.global.ForeignFixture;
import com.hellomeritz.global.RestDocsSupport;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.result.ForeignCreateResult;
import com.hellomeritz.member.service.dto.result.ForeignInfoSaveResult;
import com.hellomeritz.member.service.dto.result.ForeignSaveIpAddressResult;
import com.hellomeritz.member.service.dto.result.UserCheckIsFcResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerDocsTest extends RestDocsSupport {

    MemberService memberService = mock(MemberService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @DisplayName("외국인 유저를 생성하는 API")
    @Test
    void sendChatMessageToTranslate() throws Exception {
        ForeignCreateResult result = ForeignFixture.foreignCreateResult();
        given(memberService.createForeignMember()).willReturn(result);

        mockMvc.perform(post("/users"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-foreigner",
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("생성된 user id")
                        )
                ));
    }

    @DisplayName("외국인 유저의 설문지를 작성할 때 그 정보를 저장하는 API")
    @Test
    void saveForeignInfo() throws Exception {
        ForeignInfoSaveResult result = ForeignFixture.foreignInfoSaveResult();
        given(memberService.saveForeignInfo(any())).willReturn(result);

        mockMvc.perform(put("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(ForeignFixture.foreignInfoSaveRequest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("save-foreignerInfo",
                        pathParameters(
                                parameterWithName("userId").description("사용자의 ID")
                        ),
                        requestFields(
                                fieldWithPath("language").type(JsonFieldType.STRING).description("사용자 언어: " +
                                        Arrays.stream(SourceLanguage.values())
                                                .map(Enum::name)
                                                .collect(Collectors.joining(", "))),
                                fieldWithPath("visaType").type(JsonFieldType.STRING).description("비자 타입: " +
                                        Arrays.stream(VisaType.values())
                                                .map(Enum::name)
                                                .collect(Collectors.joining(", "))),
                                fieldWithPath("hasResidentCard").type(JsonFieldType.BOOLEAN).description("외국인 거주증 보유 여부"),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING)
                                        .description("외국인의 생년월일, 입력 형식 yyyyMMdd, 최대는 오늘, 최소는 1800년 1월 1일로 설정하였으며 validation 체크를 합니다.")

                        ),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 ID")
                        )
                ));
    }

    @DisplayName("외국인 유저의 설문지를 작성할 때 그 정보를 저장하는 API")
    @Test
    void saveForeignIpAddress() throws Exception {
        ForeignSaveIpAddressResult result = ForeignFixture.foreignSaveIpAddressResult();
        given(memberService.saveForeignIpAddress(any())).willReturn(result);

        mockMvc.perform(patch("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("save-foreigner-ipAddress",
                        pathParameters(
                                parameterWithName("userId").description("사용자의 ID")
                        ),
                        responseFields(
                                fieldWithPath("ipAddress").type(JsonFieldType.STRING).description("외국인 IP ADDRESS")
                        )
                ));
    }

    @DisplayName("해당 유저가 외국인인지 확인하는 API")
    @Test
    void checkUserIsFc() throws Exception {
        UserCheckIsFcResult result = ForeignFixture.userCheckIsFcResult();
        given(memberService.checkUserIsFc(any())).willReturn(result);

        mockMvc.perform(get("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("check-isFc",
                        pathParameters(
                                parameterWithName("userId").description("사용자의 ID")
                        ),
                        responseFields(
                                fieldWithPath("isFC").type(JsonFieldType.BOOLEAN).description("설계사인가? true, false")
                        )
                ));
    }

    @DisplayName("외국인 채팅방에 입장했을 때 설계사에게 알람을 주는 API")
    @Test
    void notifyForeignerArrival() throws Exception {

        mockMvc.perform(get("/users/{fcId}/alarm", 1L)
                .param("chatRoomId","1"))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("notify-foreigner-arrival-to-fc",
                pathParameters(
                    parameterWithName("fcId").description("설계사의 ID")
                ),
                queryParameters(
                    parameterWithName("chatRoomId").description("외국인이 입장한 채팅방의 번호")
                )
            ));
    }


}
