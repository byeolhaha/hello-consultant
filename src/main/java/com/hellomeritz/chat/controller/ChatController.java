package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatMessageGetRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hellomeritz.chat.controller.dto.response.ChatMessageGetResponses;
import com.hellomeritz.chat.controller.dto.response.ChatMessageTranslateResponse;
import com.hellomeritz.chat.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ChatMessageTranslateResponse> sendChatMessage(
        @RequestBody ChatMessageTranslateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ChatMessageTranslateResponse.to(
                chatService.translateText(request.toChatMessageTextParam())
            ));
    }

    @PostMapping(
        path = "/audios",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> sendAudioChatMessage(
        @RequestPart MultipartFile audioFile,
        @RequestPart ChatMessageSttRequest request) {
        chatService.sendAudioMessage(request.toChatMessageSttParam(audioFile));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
