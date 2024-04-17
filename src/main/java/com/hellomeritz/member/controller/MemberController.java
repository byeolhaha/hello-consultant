package com.hellomeritz.member.controller;

import com.hellomeritz.member.controller.dto.ForeignCreateResponse;
import com.hellomeritz.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ForeignCreateResponse> createUser() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ForeignCreateResponse.to(
                        memberService.createForeignMember())
                );
    }


}
