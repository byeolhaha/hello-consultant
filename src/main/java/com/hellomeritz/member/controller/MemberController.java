package com.hellomeritz.member.controller;

import com.hellomeritz.member.controller.dto.ForeignCreateResponse;
import com.hellomeritz.member.controller.dto.ForeignInfoSaveRequest;
import com.hellomeritz.member.controller.dto.ForeignInfoSaveResponse;
import com.hellomeritz.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(
            path = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ForeignInfoSaveResponse> saveForeignInfo(
            @RequestBody @Valid ForeignInfoSaveRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ForeignInfoSaveResponse.to(
                        memberService.saveForeignInfo(
                                request.toForeignInfoSaveParam()
                        ))
                );
    }

}
