package com.hello.chat.controller;

import com.hello.chat.controller.dto.request.ChatMessageSttRequest;
import com.hello.chat.controller.dto.request.ChatMessageTranslateRequest;
import com.hello.chat.controller.dto.response.ChatMessageSttResponse;
import com.hello.chat.controller.dto.response.ChatMessageTranslateResponses;
import com.hello.chat.service.ChatMessageService;
import com.hello.chat.service.dto.result.ChatMessageSttResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chats/{chatRoomId}")
    @SendTo("/queue/chats/{chatRoomId}")
    public ResponseEntity<ChatMessageTranslateResponses> sendChatMessage(
        @DestinationVariable("chatRoomId") Long chatRoomId,
        @Payload @Valid ChatMessageTranslateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ChatMessageTranslateResponses.to(
                chatMessageService.translateText(request.toChatMessageTextParam(chatRoomId))
            ));
    }

    @PostMapping(
        path = "/chats/{chatRoomId}/stt",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ChatMessageSttResponse> uploadAudio(
        @PathVariable
        @Positive(message = "chatRoomId는 음수이거나 0일 수 없습니다.")
        Long chatRoomId,
        @RequestPart
        @NotBlank(message = "audio 파일은 빈값일 수 없습니다.")
        MultipartFile audioFile,
        @RequestPart
        @Valid
        ChatMessageSttRequest request) {
        ChatMessageSttResult result
            = chatMessageService.sendAudioMessage(request.toChatMessageSttParam(audioFile, chatRoomId));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ChatMessageSttResponse.to(result));
    }

}
