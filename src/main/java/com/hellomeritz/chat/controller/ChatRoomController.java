package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageGetRequest;
import com.hellomeritz.chat.controller.dto.request.ChatRoomCreateRequest;
import com.hellomeritz.chat.controller.dto.response.ChatMessageGetResponses;
import com.hellomeritz.chat.controller.dto.response.ChatRoomCreateResponse;
import com.hellomeritz.chat.controller.dto.response.ChatRoomUserInfoResponse;
import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.param.ChatRoomUserInfoParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/chat-rooms")
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
}
