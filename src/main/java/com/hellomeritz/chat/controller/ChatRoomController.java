package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatRoomCreateRequest;
import com.hellomeritz.chat.controller.dto.response.ChatRoomCreateResponse;
import com.hellomeritz.chat.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        @RequestBody ChatRoomCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ChatRoomCreateResponse.to(
                    chatService.createChatRoom(request.toChatRoomCreateRequest())
                )
            );
    }
}
