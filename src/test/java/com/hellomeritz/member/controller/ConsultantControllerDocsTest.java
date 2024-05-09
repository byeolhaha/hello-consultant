package com.hellomeritz.member.controller;

import com.hellomeritz.global.FinancialConsultantFixture;
import com.hellomeritz.global.ForeignFixture;
import com.hellomeritz.global.RestDocsSupport;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.result.ConsultantMatchResult;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
}
