package com.hellomeritz.chat.repository.chatsession;

import com.hellomeritz.chat.repository.chatentry.dto.ChatRoomEntryDeleteRepositoryRequest;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class ChatSession {

    private Long userId;
    private Boolean isFC;
    private Long chatRoomId;

    private ChatSession(
        Long userId,
        Boolean isFC
    ) {
        Assert.notNull(userId,"userId는 null일 수 없습니다.");
        Assert.isTrue(userId>0,"userId는 양수여야 합니다.");

        Assert.notNull(isFC,"isFC는 null일 수 없습니다.");

        this.userId = userId;
        this.isFC = isFC;
    }

    public static ChatSession to(
        Long userId,
        Boolean isFC
    ){
        return new ChatSession(
            userId,
            isFC
        );
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public ChatRoomEntryDeleteRepositoryRequest toChatRoomEntryDeleteRepositoryRequest(
        String sessionId
    ) {
        return new ChatRoomEntryDeleteRepositoryRequest(
            chatRoomId,
            sessionId
        );
    }

}
