package com.hellomeritz.chat.repository.chatmessage;

import com.hellomeritz.chat.domain.ChatMessage;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage);
}
