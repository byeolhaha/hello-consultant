package com.hellomeritz.member.controller;

import com.hellomeritz.global.FinancialConsultantFixture;
import com.hellomeritz.global.ForeignFixture;
import com.hellomeritz.global.RestDocsSupport;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.result.ConsultantLoginResult;
import com.hellomeritz.member.service.dto.result.ConsultantMatchResult;
import com.hellomeritz.member.service.dto.result.ConsultantSignUpResult;
import com.hellomeritz.member.service.dto.result.UserCheckIsFcResult;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultantControllerDocsTest extends RestDocsSupport {

    MemberService memberService = mock(MemberService.class);

    @Override
    protected Object initController() {
        return new ConsultantController(memberService);
    }

    @DisplayName("상담이 가능한 상담원을 매칭해주는 API")
    @Test
    void checkUserIsFc() throws Exception {
        ConsultantMatchResult result = FinancialConsultantFixture.consultantMatchResult();
        given(memberService.matchConsultant()).willReturn(result);

        mockMvc.perform(patch("/consultants")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("match-consultant",
                responseFields(
                    fieldWithPath("fcId").type(JsonFieldType.NUMBER).description("상담 가능한 상담원의 ID")
                )
            ));
    }

    @DisplayName("상담이 끝나면 상담원의 상태를 가능 상태로 변경하는 API")
    @Test
    void endConsultation() throws Exception {
        mockMvc.perform(put("/consultants/{fcId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("end-consultation-and-change-available",
                pathParameters(
                    parameterWithName("fcId").description("상담원의 ID")
                )
            ));
    }

    @DisplayName("상담원 회원가입 API")
    @Test
    void signUp() throws Exception {
        ConsultantSignUpResult result = FinancialConsultantFixture.consultantSignUpResult();
        given(memberService.signUp(any())).willReturn(result);

        mockMvc.perform(post("/consultants")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(FinancialConsultantFixture.consultantSignUpRequest())))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("sign-up-consultant",
                requestFields(
                    fieldWithPath("employeeNumber").type(JsonFieldType.STRING).description("설계사 사원번호"),
                    fieldWithPath("consultantPassword").type(JsonFieldType.STRING).description("설계사 비밀번호"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("설계사 전화번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("profileUrl").type(JsonFieldType.STRING).description("프로필 url"),
                    fieldWithPath("introduceMessage").type(JsonFieldType.STRING).description("소개 메세지")

                ),
                responseFields(
                    fieldWithPath("fcId").type(JsonFieldType.NUMBER).description("회원 가입된 상담원의 ID")
                )
            ));
    }

    @DisplayName("상담원 로그인 API")
    @Test
    void login() throws Exception {
        ConsultantLoginResult result = FinancialConsultantFixture.consultantLoginResult();
        given(memberService.login(any())).willReturn(result);

        mockMvc.perform(patch("/consultants/passwords")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(FinancialConsultantFixture.consultantLoginRequest())))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("login-consultant",
                requestFields(
                    fieldWithPath("employeeNumber").type(JsonFieldType.STRING).description("설계사 사원번호"),
                    fieldWithPath("consultantPassword").type(JsonFieldType.STRING).description("설계사 비밀번호")
                ),
                responseFields(
                    fieldWithPath("isMyConsultant").type(JsonFieldType.BOOLEAN).description("로그인한 사람이 우리 상담원인가?"),
                    fieldWithPath("fcId").type(JsonFieldType.NUMBER).description("설계사 ID")
                )
            ));
    }

}
