package com.hello.member.controller;

import com.hello.member.controller.dto.request.ConsultantLoginRequest;
import com.hello.member.controller.dto.request.ConsultantSignUpRequest;
import com.hello.member.controller.dto.response.ConsultantLoginResponse;
import com.hello.member.controller.dto.response.ConsultantMatchResponse;
import com.hello.member.controller.dto.response.ConsultantSignUpResponse;
import com.hello.member.service.MemberService;
import com.hello.member.service.dto.param.FinancialConsultantChangeStateParam;
import com.hello.member.service.dto.result.ConsultantSignUpResult;
import jakarta.validation.Valid;
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
    public ResponseEntity<ConsultantMatchResponse> matchConsultant() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ConsultantMatchResponse.to(memberService.matchConsultant()));
    }

    @PutMapping(
        path = "/{fcId}"
    )
    public ResponseEntity<Void> endConsultation(
        @Positive(message = "fcId는 양수여야 합니다.")
        @NotNull(message = "fcId는 null일 수 없습니다.")
        @PathVariable Long fcId
    ) {
        memberService.endConsultation(FinancialConsultantChangeStateParam.to(fcId));

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ConsultantSignUpResponse> signUp(
        @Valid @RequestBody ConsultantSignUpRequest request
    ) {
        ConsultantSignUpResult consultantSignUpResult = memberService.signUp(request.toConsultantSignUpParam());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ConsultantSignUpResponse.to(consultantSignUpResult));
    }

    @PatchMapping(
        path = "/passwords",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ConsultantLoginResponse> login(
        @Valid @RequestBody ConsultantLoginRequest request
    ) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(ConsultantLoginResponse.to(memberService.login(request.toConsultantLoginParam())));
    }

}
