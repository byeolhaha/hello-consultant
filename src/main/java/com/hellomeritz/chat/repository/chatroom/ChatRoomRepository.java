package com.hellomeritz.chat.repository.chatroom;

import com.hellomeritz.chat.domain.ChatRoom;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    ChatRoom getChatRoom(long fcId, long userId);
}
