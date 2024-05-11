package com.hellomeritz.member.controller;

import com.hellomeritz.member.controller.dto.response.ConsultantMatchResponse;
import com.hellomeritz.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
