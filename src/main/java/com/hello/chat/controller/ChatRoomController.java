package com.hello.chat.controller;

import com.hello.chat.controller.dto.request.*;
import com.hello.chat.controller.dto.response.*;
import com.hello.chat.service.ChatService;
import com.hello.chat.service.dto.param.ChatRoomUserInfoParam;
import com.hello.chat.service.dto.result.ChatRoomInfoOfConsultantResults;
import com.hello.chat.service.dto.result.ChatRoomInfoOfForeignerResults;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat-rooms")
@RestController
public class ChatRoomController {

    private final ChatService chatService;

    public ChatRoomController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(
        @RequestBody @Valid ChatRoomCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ChatRoomCreateResponse.to(
                    chatService.createChatRoom(request.toChatRoomCreateRequest())
                )
            );
    }

    @GetMapping(
        path = "/{chatRoomId}/messages",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatMessageGetResponses> getChatMessages(
        @PathVariable @Positive Long chatRoomId,
        @ModelAttribute @Valid ChatMessageGetRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatMessageGetResponses.to(
                chatService.getChatMessages(request.toChatMessageGetParam(chatRoomId))
            ));
    }

    @GetMapping(
        path = "/{chatRoomId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatRoomUserInfoResponse> getChatRoomUserInfo(
        @PathVariable @Positive Long chatRoomId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatRoomUserInfoResponse.to(
                chatService.getChatRoomUserInfo(ChatRoomUserInfoParam.to(chatRoomId))
            ));
    }

    @PostMapping(
        path = "/{chatRoomId}/passwords"
    )
    public ResponseEntity<Void> createPassword(
        @Positive(message = "chatRoomId는 양수여야 합니다.")
        @NotNull(message = "chatRoomId는 null값 일 수 없습니다.")
        @PathVariable
        Long chatRoomId,
        @Valid @RequestBody ChatRoomPasswordCreateRequest request) {
        chatService.createChatRoomPassword(request.toChatRoomPasswordCreateParam(chatRoomId));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(
        path = "/{chatRoomId}/passwords"
    )
    public ResponseEntity<ChatRoomPasswordCheckResponse> checkPassword(
        @Positive(message = "chatRoomId는 양수여야 합니다.")
        @NotNull(message = "chatRoomId는 null값 일 수 없습니다.")
        @PathVariable
        Long chatRoomId,
        @Valid
        @RequestBody
        ChatRoomPasswordCheckRequest request) {
        boolean isMyUser = chatService.checkChatRoomPassword
            (request.toChatRoomPasswordCheckParam(chatRoomId));

        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatRoomPasswordCheckResponse.to(isMyUser));
    }

    @PatchMapping(
        path = "/{chatRoomId}/messages",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> enterChatRoom(
        @Positive(message = "chatRoomId는 양수여야 합니다.")
        @NotNull(message = "chatRoomId는 null값 일 수 없습니다.")
        @PathVariable
        Long chatRoomId,
        @Valid
        @RequestBody
        ChatRoomEnterRequest request
    ) {
        chatService.enterChatRoom(request.toChatRoomEnterParam(chatRoomId));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(
        path = "/consultants",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatRoomInfoOfConsultantResponses> getRoomInfoOfConsultant(
        @Valid @ModelAttribute ChatRoomInfoOfConsultantRequest request
    ) {
        ChatRoomInfoOfConsultantResults chatRoomInfoOfConsultant
            = chatService.getChatRoomInfoOfConsultant(request.toChatRoomInfoParam());

        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatRoomInfoOfConsultantResponses.to(chatRoomInfoOfConsultant));
    }

    @GetMapping(
        path = "/foreigners",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatRoomInfoOfForeignerResponses> getRoomInfoOfForeigner(
        @Valid @ModelAttribute ChatRoomInfoOfForeignerRequest request
    ) {
        ChatRoomInfoOfForeignerResults chatRoomInfoOfForeigner
            = chatService.getChatRoomInfoOfForeigner(request.toChatRoomInfoParam());

        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatRoomInfoOfForeignerResponses.to(chatRoomInfoOfForeigner));
    }

    @PatchMapping(
        path = "/{chatRoomId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> leaveChatRoom(
        @Positive(message = "chatRoomId는 양수여야 합니다.")
        @NotNull(message = "chatRoomId는 null값 일 수 없습니다.")
        @PathVariable
        Long chatRoomId,
        @Valid
        @RequestBody
        ChatRoomLeaveRequest request
    ) {
        chatService.leaveChatRoom(request.toChatRoomLeaveParam(chatRoomId));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
