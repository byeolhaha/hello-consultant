package com.hello.chat.repository.chatroom;

import com.hello.chat.domain.ChatRoom;
import com.hello.chat.repository.chatroom.dto.ChatRoomGetInfoOfConsultant;
import com.hello.chat.repository.chatroom.dto.ChatRoomGetInfoOfForeigner;
import com.hello.chat.repository.chatroom.dto.ChatRoomPasswordInfo;
import com.hello.chat.repository.chatroom.dto.ChatRoomUserInfo;

import java.util.List;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    ChatRoom getChatRoom(long fcId, long userId);

    ChatRoomUserInfo getChatRoomUserInfo(Long chatRoomId);

    ChatRoom getChatRoom(long chatRoomId);

    ChatRoomPasswordInfo getChatRoomEnterInfo(long chatRoomId);

    List<ChatRoomGetInfoOfForeigner> findChatRoomsOfForeigner(Long foreignerId);

    List<ChatRoomGetInfoOfConsultant> findChatRoomsOfConsultant(Long foreignerId);
}
