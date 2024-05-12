package com.hellomeritz.member.controller;

import com.hellomeritz.member.controller.dto.response.ConsultantMatchResponse;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.param.FinancialConsultantChangeStateParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/consultants")
@RestController
public class ConsultantController {

    private final MemberService memberService;

    public ConsultantController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PatchMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ConsultantMatchResponse> matchConsultant(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(ConsultantMatchResponse.to(memberService.matchConsultant()));
    }

    @PutMapping(
        path = "/{fcId}"
    )
    public ResponseEntity<Void> endConsultation(
        @Positive (message = "fcId는 양수여야 합니다.")
        @NotNull (message = "fcId는 null일 수 없습니다.")
        @PathVariable Long fcId
    ) {
        memberService.endConsultation(FinancialConsultantChangeStateParam.to(fcId));

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

}
