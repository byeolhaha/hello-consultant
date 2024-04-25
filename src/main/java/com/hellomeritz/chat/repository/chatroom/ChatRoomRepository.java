package com.hellomeritz.chat.repository.chatroom;

import com.hellomeritz.chat.domain.ChatRoom;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hellomeritz.chat.repository.chatroom.dto.ChatRoomUserInfo;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    ChatRoom getChatRoom(long fcId, long userId);

    ChatRoomUserInfo getChatRoomUserInfo(Long chatRoomId);

    ChatRoom getChatRoom(long chatRoomId);

    ChatRoomPasswordInfo getChatRoomEnterInfo(long chatRoomId);
}
