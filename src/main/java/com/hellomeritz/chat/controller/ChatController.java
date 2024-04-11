package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageGetRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hellomeritz.chat.controller.dto.response.ChatMessageGetResponses;
import com.hellomeritz.chat.controller.dto.response.ChatMessageTranslateResponse;
import com.hellomeritz.chat.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chats")
@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatMessageTranslateResponse> translateText(
        @RequestBody ChatMessageTranslateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ChatMessageTranslateResponse.to(
                chatService.translateText(request.toChatMessageTextParam())
            ));
    }

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatMessageGetResponses> getChatMessages(
        @ModelAttribute ChatMessageGetRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatMessageGetResponses.to(
                chatService.getChatMessages(request.toChatMessageGetParam())
            ));
    }

}
