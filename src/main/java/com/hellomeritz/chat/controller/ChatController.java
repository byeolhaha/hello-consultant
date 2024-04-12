package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatAudioUploadRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageGetRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hellomeritz.chat.controller.dto.response.ChatAudioUploadResponse;
import com.hellomeritz.chat.controller.dto.response.ChatMessageGetResponses;
import com.hellomeritz.chat.controller.dto.response.ChatMessageSttResponse;
import com.hellomeritz.chat.controller.dto.response.ChatMessageTranslateResponse;
import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.ChatAudioUploadResult;
import com.hellomeritz.chat.service.dto.result.ChatMessageSttResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/chats")
@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping(
            "/{chatRoomId}"
    )
    public ResponseEntity<ChatMessageTranslateResponse> sendChatMessage(
            @RequestBody ChatMessageTranslateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatMessageTranslateResponse.to(
                        chatService.translateText(request.toChatMessageTextParam())
                ));
    }

    @PostMapping(
            path = "/{chatRoomId}/audios",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ChatAudioUploadResponse> uploadAudio(
            @PathVariable Long chatRoomId,
            @RequestPart MultipartFile audioFile,
            @RequestPart ChatAudioUploadRequest request) {
        ChatAudioUploadResult result
                = chatService.uploadAudioFile(request.toChatAudioUploadParam(audioFile, chatRoomId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatAudioUploadResponse.to(result));
    }

    @MessageMapping(
            "/audios"
    )
    public ResponseEntity<ChatMessageSttResponse> sendAudioChatMessage(
            @RequestPart ChatMessageSttRequest request) {
        ChatMessageSttResult result = chatService.sendAudioMessage(request.toChatMessageSttParam());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatMessageSttResponse.to(result));
    }

}
