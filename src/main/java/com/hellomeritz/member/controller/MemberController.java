package com.hellomeritz.member.controller;

import com.hellomeritz.member.controller.dto.request.ForeignInfoSaveRequest;
import com.hellomeritz.member.controller.dto.response.*;
import com.hellomeritz.member.service.MemberService;
import com.hellomeritz.member.service.dto.param.*;
import com.hellomeritz.member.service.dto.result.FcInfoResult;
import com.hellomeritz.member.service.dto.result.ForeignerInfoResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping(
        path = "/{fcId}/alarm"
    )
    public ResponseEntity<Void> notifyForeignerArrival(
        @PathVariable @Positive(message = "fcId는 양수여야 합니다.") Long fcId,
        @RequestParam @Positive(message = "chatRoomId는 양수여야 합니다.") Long chatRoomId
    ) {
        memberService.notifyForeignerArrival(AlarmToFcParam.to(chatRoomId, fcId));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping(
            path = "/{fcId}/fc-info"
    )
    public ResponseEntity<FcInfoGetResponse> getFcInfo(
            @PathVariable @Positive(message = "fcId는 양수여야 합니다.") Long fcId
    ){
        FcInfoResult fcInfoResult = memberService.getFinancialConsultantInfo(new FinancialConsultantInfoGetParam(fcId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(FcInfoGetResponse.of(fcInfoResult));
    }
    @GetMapping(
            path = "/{foreignerId}/foreigner-info"
    )
    public ResponseEntity<ForeignerInfoGetResponse> getForeignerInfo(
            @PathVariable @Positive(message = "foreignerId는 양수여야 합니다.") Long foreignerId
    ){
        ForeignerInfoResult foreignerInfoResult = memberService.getForeignerInfo(new ForeignerInfoGetParam(foreignerId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ForeignerInfoGetResponse.of(foreignerInfoResult));
    }
}
