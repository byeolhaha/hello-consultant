package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.controller.dto.request.ChatAudioUploadRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageSttRequest;
import com.hellomeritz.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hellomeritz.chat.controller.dto.response.ChatAudioUploadResponse;
import com.hellomeritz.chat.controller.dto.response.ChatMessageSttResponse;
import com.hellomeritz.chat.controller.dto.response.ChatMessageTranslateResponse;
import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.result.ChatAudioUploadResult;
import com.hellomeritz.chat.service.dto.result.ChatMessageSttResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chats/{chatRoomId}")
    @SendTo("/queue/chats/{chatRoomId}")
    public ResponseEntity<ChatMessageTranslateResponse> sendChatMessageWithSocket(
            @DestinationVariable("chatRoomId") Long chatRoomId,
            @Payload ChatMessageTranslateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatMessageTranslateResponse.to(
                        chatService.translateText(request.toChatMessageTextParam(chatRoomId))
                ));
    }

    @MessageMapping("/chats/{chatRoomId}/stt")
    @SendTo("/queue/chats/{chatRoomId}/stt")
    public ResponseEntity<ChatMessageSttResponse> sendAudioChatMessageWithSocket(
            @DestinationVariable("chatRoomId") Long chatRoomId,
            @Payload ChatMessageSttRequest request) {
        ChatMessageSttResult result = chatService.sendAudioMessage(request.toChatMessageSttParam(chatRoomId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatMessageSttResponse.to(result));
    }

    @PostMapping("/chats/{chatRoomId}")
    public ResponseEntity<ChatMessageTranslateResponse> sendChatMessage(
            @PathVariable @Positive(message = "chatRoomId는 양수여야 합니다.") Long chatRoomId,
            @RequestBody @Valid ChatMessageTranslateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatMessageTranslateResponse.to(
                        chatService.translateText(request.toChatMessageTextParam(chatRoomId))
                ));
    }

    @PostMapping(
            path = "/chats/{chatRoomId}/audios",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ChatAudioUploadResponse> uploadAudio(
            @PathVariable
            @Positive(message = "chatRoomId는 음수이거나 0일 수 없습니다.")
            Long chatRoomId,
            @RequestPart
            MultipartFile audioFile,
            @RequestPart
            @Valid
            ChatAudioUploadRequest request) {
        ChatAudioUploadResult result
                = chatService.uploadAudioFile(request.toChatAudioUploadParam(audioFile, chatRoomId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatAudioUploadResponse.to(result));
    }

    @PostMapping("/chats/{chatRoomId}/stt")
    public ResponseEntity<ChatMessageSttResponse> sendAudioChatMessage(
            @PathVariable @Positive(message = "chatRoomId는 양수여야 합니다.") Long chatRoomId,
            @RequestBody @Valid ChatMessageSttRequest request) {
        System.out.println("iSFC:"+request.isFC());
        ChatMessageSttResult result = chatService.sendAudioMessage(request.toChatMessageSttParam(chatRoomId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatMessageSttResponse.to(result));
    }

}
