package com.hellomeritz.global.event;

import com.hellomeritz.chat.repository.chatentry.ChatRoomEntry;
import com.hellomeritz.chat.service.ChatService;
import com.hellomeritz.chat.service.dto.param.ChatRoomEntryAddParam;
import com.hellomeritz.chat.service.dto.param.ChatSessionAddParam;
import com.hellomeritz.chat.service.dto.param.ChatSessionChangeParam;
import com.hellomeritz.chat.service.dto.result.ChatRoomEntryInSessions;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketEventHandler {

    private static final String SUBSCRIBE_URL = "/queue/chats/";

    private final ChatService chatService;

    public WebSocketEventHandler(ChatService chatService) {
        this.chatService = chatService;
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = getSessionId(event);
        Long memberId = getMemberId(event);
        Boolean isFC = getIsFc(event);

        chatService.addSession(
            new ChatSessionAddParam(
                sessionId,
                memberId,
                isFC));
    }

    @EventListener(SessionSubscribeEvent.class)
    public void onSubscribe(SessionSubscribeEvent event) {
        Long roomId = getRoomId(event);
        String sessionId = getSessionId(event);
        ChatRoomEntryInSessions chatRoomEntry = chatService.getChatEntryInSessions(sessionId);

        chatService.changeSession(new ChatSessionChangeParam(roomId, sessionId));

        chatService.addChatRoomEntry(new ChatRoomEntryAddParam(
            roomId,
            chatRoomEntry.userId(),
            chatRoomEntry.isFC(),
            sessionId
        ));
    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = getSessionId(event);
        chatService.leaveChatRoom(sessionId);
    }

    private String getSessionId(AbstractSubProtocolEvent event) {
        Object sessionIdObj = event.getMessage().getHeaders().get("simpSessionId");
        if (sessionIdObj == null) {
            throw new IllegalArgumentException("Session Id null일 수 없습니다.");
        }
        return sessionIdObj.toString();
    }


    private Long getMemberId(AbstractSubProtocolEvent event) {
        Map<String, List<String>> nativeHeaders = event.getMessage().getHeaders().get("nativeHeaders", Map.class);
        if (nativeHeaders == null) {
            throw new IllegalArgumentException("Native header는 null일 수 없습니다.");
        }

        List<String> memberIdList = nativeHeaders.get("memberId");
        if (memberIdList == null || memberIdList.isEmpty()) {
            throw new IllegalArgumentException("Member Id는 null이거나 빈 값일 수 없습니다.");
        }

        String memberId = memberIdList.get(0);
        return Long.parseLong(memberId.replaceAll("[\\[\\]]", ""));
    }

    private boolean getIsFc(AbstractSubProtocolEvent event) {
        Map<String, List<String>> nativeHeaders = event.getMessage().getHeaders().get("nativeHeaders", Map.class);
        if (nativeHeaders == null) {
            throw new IllegalArgumentException("Native header는 null일 수 없습니다.");
        }

        List<String> isFCs = nativeHeaders.get("isFC");
        if (isFCs == null || isFCs.isEmpty()) {
            throw new IllegalArgumentException("isFC는 null이거나 빈 값일 수 없습니다.");
        }

        return Boolean.parseBoolean(isFCs.get(0).replaceAll("[\\[\\]]", ""));
    }

    private Long getRoomId(AbstractSubProtocolEvent event) {
        String destination = event.getMessage().getHeaders().get("simpDestination", String.class);
        if (destination == null) {
            throw new IllegalArgumentException("Destination는 null일 수 없습니다.");
        }
        return Long.parseLong(destination.replace(SUBSCRIBE_URL, ""));
    }
}
