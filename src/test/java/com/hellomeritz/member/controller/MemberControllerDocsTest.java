package com.hellomeritz.member.controller;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.TargetLanguage;
import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.ChatMessageTranslateResult;
import com.hellomeritz.global.ChatFixture;
import com.hellomeritz.global.ForeignFixture;
import com.hellomeritz.global.RestDocsSupport;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.result.ForeignCreateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
}
