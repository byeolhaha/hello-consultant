package com.hellomeritz.member.controller;

import com.hellomeritz.member.controller.dto.*;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.param.ForeignSaveIpAddressParam;
import com.hellomeritz.member.service.dto.param.UserCheckIsFcParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
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
            @RequestBody
            @Valid
            ForeignInfoSaveRequest request,
            @PathVariable
            @NotNull(message = "userId는 null일 수 없습니다.")
            @Positive(message = "userId는 양수여야 합니다.")
            Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ForeignInfoSaveResponse.to(
                        memberService.saveForeignInfo(
                                request.toForeignInfoSaveParam(userId)
                        ))
                );
    }

    @PatchMapping(
            path = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ForeignSaveIpAddressResponse> saveForeignIpAddress(
            @PathVariable @Positive(message = "userId는 양수여야 합니다.") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ForeignSaveIpAddressResponse.to(
                        memberService.saveForeignIpAddress(ForeignSaveIpAddressParam.to(userId))
                ));
    }

    @GetMapping(
            path = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserCheckIsFcResponse> checkUserIsFc(
            @PathVariable @Positive(message = "userId는 양수여야 합니다.") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserCheckIsFcResponse.to(
                        memberService.checkUserIsFc(UserCheckIsFcParam.to(userId))
                ));
    }

}
